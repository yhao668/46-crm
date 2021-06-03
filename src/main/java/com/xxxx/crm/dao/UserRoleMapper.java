package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {
    int insert(UserRole record);
    UserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(UserRole record);


    int countUserRoleByUserId(int useId);

    int deleteUserRoleByUserId(int useId);


}