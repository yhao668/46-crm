package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User,Integer> {

    //根据名称查询
    User selectUserByName(String userName);

    /**
     * 查询所有的销售人员
     * @return
     */
    // 查询所有的销售人员
    public List<Map<String, Object>> queryAllSales();

    User queryUserByUserName(String userName);
}