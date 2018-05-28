package com.zhy.gateway.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * ResultVO
 * @param <T>
 */
@Data
public class ResultVO<T> {
    /**
     * code
     */
    private Integer code;

    /**
     * nessage
     */
    private String msg;

    /**
     * data
     * 如果data为null，就不参加序列化
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}
