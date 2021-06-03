package com.xxxx.crm.service;

import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.UserRoleMapper;
import com.xxxx.crm.utils.AssertUtil;

import com.xxxx.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class UserRoleService extends BaseService<UserRole,Integer> {

    @Resource
    private UserRoleMapper userRoleMapper;
    public void relaionUserRole(int userId, String roleIds) {
        /**
         * 用户角色分配
         *   原始角色不存在   添加新的角色记录
         *   原始角色存在     添加新的角色记录
         *   原始角色存在     清空所有角色
         *   原始角色存在     移除部分角色
         * 如何进行角色分配???
         *  如果用户原始角色存在  首先清空原始所有角色  添加新的角色记录到用户角色表
         */
        //根据用户id查询当前用户的所有角色信息
        int count = userRoleMapper.countUserRoleByUserId(userId);
        if (count > 0) {
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId) != count, "用户角色分配失败!");
        }
        if (StringUtils.isNotBlank(roleIds)) {
            //重新添加新的角色
            List<UserRole> userRoles = new ArrayList();
            System.out.println(userRoles);
            for (String s : roleIds.split(",")) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(Integer.parseInt(s));
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                userRoles.add(userRole);
            }
            System.out.println(userRoles);
            AssertUtil.isTrue(userRoleMapper.insertBatch(userRoles) < userRoles.size(), "用户角色分配失败!");
        }
    }

}
