package com.zhy.activitiserver.model;

import lombok.Data;

import java.util.Date;

@Data
public class HisTaskVO {
    private String name;
    private String assignee;
    private String processId;
    private String taskId;
    private Date startTime;
    private Date endTime;
}
