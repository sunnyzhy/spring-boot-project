package com.zhy.activitiserver.service;

import com.zhy.activitiserver.model.ApproveVO;
import com.zhy.activitiserver.model.ApplyVO;
import com.zhy.activitiserver.model.TaskVO;
import com.zhy.activitiserver.utils.ResponseVoUtil;
import com.zhy.activitiserver.vo.ResponseVO;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActivitiService {
    @Autowired
    private ResponseVoUtil responseVoUtil;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private FormService formService;
    @Autowired
    private ProcessEngine processEngine;

    private String processKey = "vaction";

    /**
     * 初始化角色
     *
     * @return
     */
    private Map<String, Object> initGroups() {
        Map<String, Object> variables = new HashMap<>();
        List<String> employee = new ArrayList<>();
        employee.add("employee");
        variables.put("employee", employee);
        List<String> manager = new ArrayList<>();
        manager.add("manager");
        variables.put("manager", manager);
        List<String> chiefInspector = new ArrayList<>();
        chiefInspector.add("chief_inspector");
        variables.put("chiefInspector", chiefInspector);
        List<String> hr = new ArrayList<>();
        hr.add("hr");
        variables.put("hr", hr);
        return variables;
    }

    /**
     * 申请
     *
     * @param applyVO
     * @return
     */
    public ResponseVO apply(ApplyVO applyVO) {
        //  初始化角色
        Map<String, Object> variables = initGroups();

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, variables);
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .singleResult();

        //  设置代理人，即当前的申请人
        taskService.setAssignee(task.getId(), applyVO.getUserId());

        //  用formService提交表单
        //  等价于用taskService.claim() + taskService.complete()
        formService.submitTaskFormData(task.getId(), applyVO.getForm());

        return responseVoUtil.success();
    }

    /**
     * 查询任务列表
     *
     * @param userId
     * @return
     */
    public ResponseVO<List<TaskVO>> getTask(String userId) {
        //  查询用户所属的角色
        List<Group> groupList = identityService.createGroupQuery()
                .groupMember(userId)
                .list();

        //  过滤出角色id
        List<String> groupIdList = groupList.parallelStream()
                .map(group -> group.getId())
                .collect(Collectors.toList());

        //  分页查询，pageNum从0开始
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey(processKey)
                .taskCandidateGroupIn(groupIdList)
                .listPage(0, 20);

        List<TaskVO> taskVOList = taskList.parallelStream()
                .map(task -> {
                    TaskVO taskVO = new TaskVO();
                    taskVO.setProcessId(task.getProcessInstanceId());
                    taskVO.setTaskId(task.getId());
                    taskVO.setCreateTime(task.getCreateTime());
                    return taskVO;
                })
                .collect(Collectors.toList());
        return responseVoUtil.success(taskVOList);
    }

//    public ResponseVO<List<TaskVO>> getHisTask(String userId) {
//        //  查询用户所属的角色
//        List<Group> groupList = identityService.createGroupQuery()
//                .groupMember(userId)
//                .list();
//
//        //  过滤出角色id
//        List<String> groupIdList = groupList.parallelStream()
//                .map(group -> group.getId())
//                .collect(Collectors.toList());
//
//        //  分页查询，pageNum从0开始
//        List<Task> taskList = historyService.createHistoricActivityInstanceQuery().createTaskQuery()
//                .processDefinitionKey(processKey)
//                .taskCandidateGroupIn(groupIdList)
//                .listPage(0, 20);
//
//        List<TaskVO> taskVOList = taskList.parallelStream()
//                .map(task -> {
//                    TaskVO taskVO = new TaskVO();
//                    taskVO.setProcessId(task.getProcessInstanceId());
//                    taskVO.setTaskId(task.getId());
//                    taskVO.setCreateTime(task.getCreateTime());
//                    return taskVO;
//                })
//                .collect(Collectors.toList());
//        return responseVoUtil.success(taskVOList);
//    }

    /**
     * 审批
     *
     * @param approveVO
     * @return
     */
    public ResponseVO approve(ApproveVO approveVO) {
        Task task = taskService.createTaskQuery()
                .taskId(approveVO.getTaskId())
                .singleResult();
        if (task == null) {
            return responseVoUtil.error(-1, "任务不存在或已结束");
        }

        //  设置代理人，即当前的审批人
        taskService.setAssignee(task.getId(), approveVO.getUserId());

        //  提交审批的表单
        formService.submitTaskFormData(approveVO.getTaskId(), approveVO.getForm());
        return responseVoUtil.success();
    }

    /**
     * 流程图
     *
     * @param processInstanceId
     * @return
     */
    public InputStream getDiagram(String processInstanceId) {
        //获得流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = StringUtils.EMPTY;
        if (processInstance == null) {
            //查询已经结束的流程实例
            HistoricProcessInstance processInstanceHistory =
                    historyService.createHistoricProcessInstanceQuery()
                            .processInstanceId(processInstanceId).singleResult();
            if (processInstanceHistory == null) {
                return null;
            } else {
                processDefinitionId = processInstanceHistory.getProcessDefinitionId();
            }
        } else {
            processDefinitionId = processInstance.getProcessDefinitionId();
        }

        //使用宋体
        String fontName = "宋体";
        //获取BPMN模型对象
        BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);
        //获取流程实例当前的节点，需要高亮显示
        List<String> currentActs = Collections.EMPTY_LIST;
        if (processInstance != null)
            currentActs = runtimeService.getActiveActivityIds(processInstance.getId());

        return processEngine.getProcessEngineConfiguration()
                .getProcessDiagramGenerator()
                .generateDiagram(model, "png", currentActs, new ArrayList<String>(),
                        fontName, fontName, fontName, null, 1.0);
    }
}
