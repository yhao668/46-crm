package com.xxxx.crm.service;

import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.ModuleMapper;
import com.xxxx.crm.dao.PermissionMapper;
import com.xxxx.crm.dto.TreeDto;
import com.xxxx.crm.vo.Module;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModuleService extends BaseService<Module,Integer> {

    @Resource
    private ModuleMapper moduleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    /**
     * todo 查询所有模块已tree形式展示 (终版)
     * @param roleId
     * @return
     */
    public List<TreeDto> queryAllModules2(Integer roleId){
        List<TreeDto> treeDtos = moduleMapper.queryAllModules();
        //根据角色id查询角色拥有的菜单id  List<Integer>
        List<Integer> roleHasMids =  permissionMapper.queryRoleHasAllModuleIdsByRoleId(roleId);
        if (roleHasMids != null && roleHasMids.size()>0){
            treeDtos.forEach(treeDto -> {
                if (roleHasMids.contains(treeDto.getId())){
                    treeDto.setChecked(true);
                }
            });
        }
        return treeDtos;
    }

    public Map<String,Object> moduleList(){
        Map<String,Object> result = new HashMap<String,Object>();
        List<Module> modules =moduleMapper.queryModules();
        result.put("count",modules.size());
        result.put("data",modules);
        result.put("code",0);
        result.put("msg","");
        return result;
    }

}
