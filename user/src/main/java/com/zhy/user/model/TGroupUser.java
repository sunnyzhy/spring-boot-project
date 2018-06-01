package com.zhy.user.model;

import javax.persistence.*;

@Table(name = "t_group_user")
public class TGroupUser {
    /**
     * 组-用户id，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 分组id
     */
    @Column(name = "group_id")
    private Integer groupId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 获取组-用户id，主键，自增
     *
     * @return id - 组-用户id，主键，自增
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置组-用户id，主键，自增
     *
     * @param id 组-用户id，主键，自增
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取分组id
     *
     * @return group_id - 分组id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * 设置分组id
     *
     * @param groupId 分组id
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}