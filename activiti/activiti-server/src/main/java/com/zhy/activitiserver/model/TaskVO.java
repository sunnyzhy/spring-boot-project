package com.zhy.activitiserver.model;

import lombok.Data;

import java.util.Date;

@Data
public class TaskVO {
    private String name;
    private String assignee;
    private String processId;
    private String taskId;
    private Date createTime;
}
