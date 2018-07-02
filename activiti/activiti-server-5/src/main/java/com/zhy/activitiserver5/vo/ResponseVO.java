package com.zhy.activitiserver5.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ResponseVO<T> {
    private Integer code;
    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}
