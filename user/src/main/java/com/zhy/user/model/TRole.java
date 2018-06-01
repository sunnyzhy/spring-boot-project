package com.zhy.user.model;

import javax.persistence.*;

@Table(name = "t_role")
public class TRole {
    /**
     * 角色id，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 获取角色id，主键，自增
     *
     * @return id - 角色id，主键，自增
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置角色id，主键，自增
     *
     * @param id 角色id，主键，自增
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取角色名称
     *
     * @return name - 角色名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名称
     *
     * @param name 角色名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取角色描述
     *
     * @return description - 角色描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置角色描述
     *
     * @param description 角色描述
     */
    public void setDescription(String description) {
        this.description = description;
    }
}