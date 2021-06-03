package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {
    /**
     * todo 查询角色列表
     */
    public List<Map<String,Object>> queryAllRoles(Integer userId);

    /**
     * todo 根据角色名查询角色
     * @param roleName
     * @return
     */
    Role queryRoleByRoleName(String roleName);
}