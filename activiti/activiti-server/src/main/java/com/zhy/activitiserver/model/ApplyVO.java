package com.zhy.activitiserver.model;

import lombok.Data;

import java.util.Map;

@Data
public class ApplyVO {
    private String userId;
    private Map<String,String> form;
}
