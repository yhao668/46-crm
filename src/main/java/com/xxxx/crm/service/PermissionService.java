package com.xxxx.crm.service;

import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.PermissionMapper;
import com.xxxx.crm.vo.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionService extends BaseService<Permission,Integer> {

    @Resource
    private PermissionMapper permissionMapper;

    /**
     * todo 根据用户id查询用户拥有的权限
     * @param userId
     * @return
     */
    public List<Integer> queryUserHasRolesHasPermissions(Integer userId){
        return permissionMapper.queryUserHasRolesHasPermissions(userId);
    }
}
