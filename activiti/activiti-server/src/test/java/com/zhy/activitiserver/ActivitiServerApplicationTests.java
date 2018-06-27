package com.zhy.activitiserver;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiServerApplicationTests {
	@Autowired
	private HistoryService historyService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	private String processKey = "vaction";
	@Test
	public void contextLoads() {
	}

	// 历史流程实例查看（查找按照某个规则一共执行了多少次流程）
	@Test
	public void queryHistoricProcessInstance() throws Exception {
		// 获取历史流程实例的查询对象
		HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService
				.createHistoricProcessInstanceQuery();
		// 设置查询参数
		historicProcessInstanceQuery
				//过滤条件
				.processDefinitionKey(processKey)
				.involvedUser("tom")
				.finished()
				// 分页条件
//     .listPage(firstResult, maxResults)
				// 排序条件
				.orderByProcessInstanceStartTime().desc();
		// 执行查询
		List<HistoricProcessInstance> hpis = historicProcessInstanceQuery.list();
		// 遍历查看结果
		for (HistoricProcessInstance hpi : hpis) {
			System.out.print("pid:" + hpi.getId()+",");
			System.out.print("pdid:" + hpi.getProcessDefinitionId()+",");
			System.out.print("startTime:" + hpi.getStartTime()+",");
			System.out.print("endTime:" + hpi.getEndTime()+",");
			System.out.print("duration:" + hpi.getDurationInMillis()+",");
			System.out.println("vars:" + hpi.getProcessVariables());
		}

	}

	// 历史活动查看(某一次流程的执行经历的多少步)
	@Test
	public void queryHistoricActivityInstance() throws Exception {
		List<HistoricActivityInstance> hais = historyService
				.createHistoricActivityInstanceQuery()
				// 过滤条件
				.processInstanceId("232501" )
				// 分页条件
//     .listPage(firstResult, maxResults)
				// 排序条件
				.orderByHistoricActivityInstanceEndTime().asc()
				// 执行查询
				.list();
		for (HistoricActivityInstance hai : hais) {
			System.out.print("activitiId:" + hai.getActivityId()+"，");
			System.out.print("name:" + hai.getActivityName()+"，");
			System.out.print("type:" + hai.getActivityType()+"，");
			System.out.print("pid:" + hai.getProcessInstanceId()+"，");
			System.out.print("assignee:" + hai.getAssignee()+"，");
			System.out.print("startTime:" + hai.getStartTime()+"，");
			System.out.print("endTime:" + hai.getEndTime()+"，");
			System.out.println("duration:" + hai.getDurationInMillis());
		}
	}

	/**
	 * 查询当前用户的已办任务
	 */
	@Test
	public void getFinishedTask(){
		List<HistoricTaskInstance>  hisTaskList = historyService
				.createHistoricTaskInstanceQuery()
				.taskAssignee("lucy")
				.finished()
				.orderByTaskId()
				.desc()
				.list();
		for(HistoricTaskInstance taskInstance : hisTaskList){
			System.out.print(taskInstance.getProcessInstanceId()+"，");
			System.out.print(taskInstance.getName()+"，");
			System.out.print(taskInstance.getStartTime()+"，");
			System.out.println(taskInstance.getEndTime());
		}
	}

	/**
	 * 查询当前用户的待办任务
	 */
	@Test
	public void getTask(){
		List<String> groupIdList = new ArrayList<>();
		groupIdList.add("hr");
		//创建查询对象
		List<Task> list = taskService.createTaskQuery()
				.processDefinitionKey(processKey)
				.taskCandidateGroupIn(groupIdList)
				.list();

		for (Task task : list) {

			//流程实例id
			String processInstanceId = task.getProcessInstanceId();
			//根据流程实例id找到流程实例对象
			ProcessInstance processInstance = runtimeService
					.createProcessInstanceQuery()
					.processInstanceId(processInstanceId)
					.singleResult();
			//从流程实例对象获取bussinesskey
			String businessKey = processInstance.getBusinessKey();
			//根据businessKey查询业务系统，获取相关的业务信息
			System.out.println("-------------------");
			System.out.println("流程实例id：" + task.getProcessInstanceId());
			System.out.println("任务id：" + task.getId());
			System.out.println("任务标识：" + task.getTaskDefinitionKey());
			System.out.println("任务负责人：" + task.getAssignee());
			System.out.println("任务名称：" + task.getName());
			System.out.println("任务创建时间：" + task.getCreateTime());
			System.out.println("+++++++++++++++");
		}
	}
}
