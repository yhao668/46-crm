package com.xxxx.crm.base;


public class BaseQuery {
    private Integer page=1;//页码
    private Integer limit=10;//条数

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
