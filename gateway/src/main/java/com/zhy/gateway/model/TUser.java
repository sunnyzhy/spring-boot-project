package com.zhy.gateway.model;

import lombok.Data;

@Data
public class TUser {
    /**
     * 用户id，主键，自增
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 登录的用户名
     */
    private String userName;

    /**
     * 登录的密码
     */
    private String password;

    /**
     * 性别
     */
    private String sex;

    /**
     * 用户状态 0:注销 1:正常
     */
    private Byte status;

    /**
     * 用户类型 0:普通用户 1:管理员
     */
    private Byte type;
}