package com.zhy.activitiserver5.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageVO<T> {
    private List<T> records;
    private Integer count;
}
