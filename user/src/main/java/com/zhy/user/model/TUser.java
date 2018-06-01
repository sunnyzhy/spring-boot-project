package com.zhy.user.model;

import javax.persistence.*;

@Table(name = "t_user")
public class TUser {
    /**
     * 用户id，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 登录的用户名
     */
    @Column(name = "user_name")
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

    /**
     * 获取用户id，主键，自增
     *
     * @return id - 用户id，主键，自增
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置用户id，主键，自增
     *
     * @param id 用户id，主键，自增
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取登录的用户名
     *
     * @return user_name - 登录的用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置登录的用户名
     *
     * @param userName 登录的用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取登录的密码
     *
     * @return password - 登录的密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置登录的密码
     *
     * @param password 登录的密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取性别
     *
     * @return sex - 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取用户状态 0:注销 1:正常
     *
     * @return status - 用户状态 0:注销 1:正常
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置用户状态 0:注销 1:正常
     *
     * @param status 用户状态 0:注销 1:正常
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取用户类型 0:普通用户 1:管理员
     *
     * @return type - 用户类型 0:普通用户 1:管理员
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置用户类型 0:普通用户 1:管理员
     *
     * @param type 用户类型 0:普通用户 1:管理员
     */
    public void setType(Byte type) {
        this.type = type;
    }
}