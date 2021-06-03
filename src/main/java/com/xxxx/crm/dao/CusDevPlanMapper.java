package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.CusDevPlan;

public interface CusDevPlanMapper extends BaseMapper<CusDevPlan,Integer> {
    CusDevPlan selectByPrimaryKey(Integer id);
    int updateByPrimaryKey(CusDevPlan record);
}