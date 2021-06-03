package com.xxxx.crm.service;

import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.ModuleMapper;
import com.xxxx.crm.dao.PermissionMapper;
import com.xxxx.crm.dao.RoleMapper;

import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.Permission;
import com.xxxx.crm.vo.Role;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RoleService extends BaseService<Role, Integer> {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private ModuleMapper moduleMapper;

    /**
     * todo 查询角色列表
     *
     * @return
     */
    public List<Map<String, Object>> queryAllRoles(Integer userId) {
        return roleMapper.queryAllRoles(userId);
    }


    /**
     * todo 角色添加
     *
     * @param role
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveRole(Role role) {
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()), "请输入角色名!!!");
        Role temp = roleMapper.queryRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(temp != null, "该角色已存在!!!");
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(role) < 1, "角色记录添加失败!!!");
    }

    /**
     * todo 角色更新
     *
     * @param role
     */
    public void updateRole(Role role) {
        AssertUtil.isTrue(role.getId() == null || selectByPrimaryKey(role.getId()) == null, "待修改记录不存在!!!");
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()), "请输入角色名!!!");
        Role temp = roleMapper.queryRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(temp != null && !(temp.getId().equals(role.getId())), "该角色已存在!!!");
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(role) < 1, "角色记录更新失败!!!");
    }

    /**
     * todo 删除角色记录
     *
     * @param id
     */
    public void deleteRole(Integer id) {
        Role temp = selectByPrimaryKey(id);
        AssertUtil.isTrue(temp == null || id == null, "待删除记录不存在!!!");
        temp.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp) < 1, "角色记录删除失败!!!");
    }

    /**
     * todo 根据 模块id和角色id增加权限
     * @param mids
     * @param roleId
     */
    public void addGrant(Integer[] mids, Integer roleId) {
        /**
         * 核心表-t_permission  t_role(校验角色存在)
         *   如果角色存在原始权限  删除角色原始权限
         *     然后添加角色新的权限 批量添加权限记录到t_permission
         */


        Role temp = selectByPrimaryKey(roleId);
        AssertUtil.isTrue(temp == null || roleId == null, "待授权的角色不存在!!!");
        int count = permissionMapper.countPermissionByRoleId(roleId);
        if (count > 0) {
            AssertUtil.isTrue(permissionMapper.deletePermissionsByRoleId(roleId) < count, "权限分配失败!!!");
        }
        if (mids != null && mids.length > 0) {
            List<Permission> permissionList = new ArrayList<>();
            for (Integer mid:mids){
                Permission permission = new Permission();
                permission.setRoleId(roleId);
                permission.setModuleId(mid);
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());
                permission.setAclValue(moduleMapper.selectByPrimaryKey(mid).getOptValue());

                permissionList.add(permission);
            }
            permissionMapper.insertBatch(permissionList);
        }
    }
}
