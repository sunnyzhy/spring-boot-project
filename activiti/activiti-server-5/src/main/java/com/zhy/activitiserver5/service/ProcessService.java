package com.zhy.activitiserver5.service;

import com.zhy.activitiserver5.activiti.diagram.MyProcessDiagramGenerator;
import com.zhy.activitiserver5.utils.FileUtil;
import com.zhy.activitiserver5.utils.ResponseVoUtil;
import com.zhy.activitiserver5.vo.ResponseVO;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhy
 * @date 2018/7/17 16:41
 **/
@Service
public class ProcessService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private MyProcessDiagramGenerator processDiagramGenerator;
    @Autowired
    private ResponseVoUtil responseVoUtil;
    @Autowired
    private FileUtil fileUtil;
    @Value("${file.local.path}")
    private String fileLocalPath;
    @Value("${file.url}")
    private String fileUrl;

    /**
     * 生成工作流图片
     *
     * @param processInstanceId
     * @return
     */
    public ResponseVO<String> getDiagram(String processInstanceId) {
        //获取工作流图片的字节流
        InputStream inputStream = getDiagramInputStream(processInstanceId);
        if (inputStream == null) {
            return responseVoUtil.error(-1, "加载工作流图片失败");
        }

        String fileName = String.format("%s/%s.activiti.png", fileLocalPath, processInstanceId);
        File file = fileUtil.init(fileLocalPath, fileName);

        //把png文件保存到nginx的文件目录
        try {
            BufferedImage image = ImageIO.read(inputStream);
            OutputStream outputStream = new FileOutputStream(file);
            ImageIO.write(image, "png", outputStream);
            outputStream.close();
            inputStream.close();
            return responseVoUtil.success(String.format("%s/%s", fileUrl, new File(fileName).getName()));
        } catch (Exception e) {
            return responseVoUtil.error(-1, e.getMessage());
        }
    }

    /**
     * 获取工作流图片的字节流
     *
     * @param processInstanceId
     * @return
     */
    private InputStream getDiagramInputStream(String processInstanceId) {
        ProcessInstance processInstance = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        BpmnModel bpmnModel;
        List<String> activeActivityIds = new ArrayList<>();
        String processDefinitionId;
        //存在活动节点，流程正在进行中
        if (processInstance != null) {
            processDefinitionId = processInstance.getProcessDefinitionId();
            //流程定义，正在活动的节点
            activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
        } else {
            //流程已经结束
            HistoricProcessInstance instance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(processInstanceId).singleResult();
            processDefinitionId = instance.getProcessDefinitionId();
        }

        bpmnModel = repositoryService.getBpmnModel(processDefinitionId);

        //-------------------------------executedActivityIdList已经执行的节点------------------------------------
        List<HistoricActivityInstance> historicActivityInstanceList = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();

        // 已执行的节点ID集合
        List<String> executedActivityIdList = new ArrayList<>();
        for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
            executedActivityIdList.add(activityInstance.getActivityId());
        }

        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);

        List<String> highLightedFlows = getHighLightedFlows((ProcessDefinitionEntity) processDefinition,
                historicActivityInstanceList);

        //生成流图片 所有走过的节点高亮 第三个参数 activeActivityIds=当前活动节点点高亮;executedActivityIdList=已经执行过的节点高亮
        InputStream inputStream = processDiagramGenerator
                .generateDiagram(bpmnModel,
                        "PNG",
                        activeActivityIds,
                        highLightedFlows,
                        processEngine.getProcessEngineConfiguration().getActivityFontName(),
                        processEngine.getProcessEngineConfiguration().getLabelFontName(),
                        processEngine.getProcessEngineConfiguration().getActivityFontName(),
                        processEngine.getProcessEngineConfiguration().getProcessEngineConfiguration().getClassLoader(),
                        1);
        return inputStream;
    }


    /**
     * 获取需要高亮的线
     *
     * @param processDefinitionEntity
     * @param historicActivityInstances
     * @return
     */
    private List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity,
                                             List<HistoricActivityInstance> historicActivityInstances) {
        // 用以保存高亮的线flowId
        List<String> highFlows = new ArrayList<>();
        // 对历史流程节点进行遍历
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {
            // 得到节点定义的详细信息
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i)
                            .getActivityId());
            // 用以保存后需开始时间相同的节点
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<>();
            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i + 1)
                            .getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                // 后续第一个节点
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);
                // 后续第二个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);
//                if (activityImpl1.getStartTime().equals(activityImpl2.getStartTime())) {
                // 如果第一个节点和第二个节点开始时间相同保存
                ActivityImpl sameActivityImpl2 = processDefinitionEntity
                        .findActivity(activityImpl2.getActivityId());
                sameStartTimeNodes.add(sameActivityImpl2);
//                } else {
//                    // 有不相同跳出循环
//                    break;
//                }
            }
            // 取出节点的所有出去的线
            List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition.getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }

}
