package com.xxxx.crm.dto;

/**
 * 接收tree数据显示在页面
 */
public class TreeDto {
    /**
     * 自己的id
     */
    private Integer id;
    /**
     * 父id
     */
    private Integer pId;
    /**
     * 模块名
     */
    private String name;
    /**
     * 是否选中
     */
    private Boolean checked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
