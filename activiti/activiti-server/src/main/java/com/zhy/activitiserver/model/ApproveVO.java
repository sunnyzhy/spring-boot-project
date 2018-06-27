package com.zhy.activitiserver.model;

import lombok.Data;

import java.util.Map;

@Data
public class ApproveVO {
    private String userId;
    private String taskId;
    private Map<String,String> form;
}
