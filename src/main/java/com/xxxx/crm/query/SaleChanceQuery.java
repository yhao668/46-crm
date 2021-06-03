package com.xxxx.crm.query;

import com.xxxx.crm.base.BaseQuery;

public class SaleChanceQuery extends BaseQuery {
    /**
     * 客户名
     */
    private String customerName;
    /**
     * 创建人
     */
    private String createMan;
    /**
     * 状态
     */
    private String state;

    private Integer devResult; // 开发状态
    private Integer assignMan;// 分配人

    public Integer getDevResult() {
        return devResult;
    }

    public void setDevResult(Integer devResult) {
        this.devResult = devResult;
    }

    public Integer getAssignMan() {
        return assignMan;
    }

    public void setAssignMan(Integer assignMan) {
        this.assignMan = assignMan;
    }

    public SaleChanceQuery() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setAggsinMan(Integer userId) {
    }
}
