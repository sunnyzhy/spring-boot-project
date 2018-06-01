package com.zhy.user.model;

import javax.persistence.*;

@Table(name = "t_group")
public class TGroup {
    /**
     * 分组id，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 分组名称
     */
    private String name;

    /**
     * 分组描述
     */
    private String description;

    /**
     * 获取分组id，主键，自增
     *
     * @return id - 分组id，主键，自增
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置分组id，主键，自增
     *
     * @param id 分组id，主键，自增
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取分组名称
     *
     * @return name - 分组名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置分组名称
     *
     * @param name 分组名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取分组描述
     *
     * @return description - 分组描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置分组描述
     *
     * @param description 分组描述
     */
    public void setDescription(String description) {
        this.description = description;
    }
}