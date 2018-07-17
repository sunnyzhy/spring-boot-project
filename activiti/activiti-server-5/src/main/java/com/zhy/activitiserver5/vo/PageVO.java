package com.zhy.activitiserver5.vo;

import lombok.Data;

import java.util.List;

/**
 * @author zhy
 * @date 2018/7/17 16:41
 **/
@Data
public class PageVO<T> {
    private List<T> records;
    private Integer count;
}
