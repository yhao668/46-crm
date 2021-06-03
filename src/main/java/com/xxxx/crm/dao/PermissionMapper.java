package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {

    /**
     * todo 根据角色id查询角色存在原始权限的个数
     * @param roleId
     * @return
     */
    int countPermissionByRoleId(Integer roleId);

    /**
     * todo 根据角色id 删除中间表的对应的模块
     * @param roleId
     * @return
     */
    int deletePermissionsByRoleId(Integer roleId);

    /**
     * todo 根据角色id 查询角色拥有的菜单id
     * @param roleId
     * @return
     */
    List<Integer> queryRoleHasAllModuleIdsByRoleId(Integer roleId);

    /**
     * todo 根据用户id查询用户拥有的权限
     * @param userId
     * @return
     */
    List<Integer> queryUserHasRolesHasPermissions(Integer userId);
}