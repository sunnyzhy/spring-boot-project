package com.zhy.dubbo.api.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhy
 * @date 2018/8/18 15:27
 * dubbo的实体类必须实现Serializable接口
 **/
@Data
public class User implements Serializable {
    private Integer id;
    private String name;
    private String phone;
}
