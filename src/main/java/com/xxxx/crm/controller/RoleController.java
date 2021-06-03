package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.query.RoleQuery;
import com.xxxx.crm.service.RoleService;
import com.xxxx.crm.vo.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
    @Resource
    private RoleService roleService;
    /**
     * 查询角色列表
     * @return
     */
    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Map<String,Object>> queryAllRoles(Integer userId){
        System.out.println(userId);
        List<Map<String, Object>> list = roleService.queryAllRoles(userId);
        return list;
    }

    @RequestMapping("index")
    public String index(){
        return "role/role";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> userList(RoleQuery roleQuery){
        return roleService.queryByParamsForTable(roleQuery);
    }

//    @RequestMapping("addOrUpdateRolePage")
//    public String addUserPage(Integer id, Model model){
//        if(null !=id){
//            model.addAttribute("role",roleService.selectByPrimaryKey(id));
//        }
//        return "role/add_update";
//    }
    @RequestMapping("addOrUpdateRolePage")
    public String add_update(Integer roleId, Model model){
        //判断
        if(roleId!=null){
            //查询目标对象
            Role role = roleService.selectByPrimaryKey(roleId);
            //查询
            model.addAttribute("role",role);
        }
        return "role/add_update";
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveRole(Role role){
        roleService.saveRole(role);
        return success("角色记录添加成功");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateRole(Role role){
        roleService.updateRole(role);
        return success("角色记录更新成功");
    }
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteRole(Integer id){
        roleService.deleteRole(id);
        return success("角色记录删除成功");
    }

    @RequestMapping("dels")
    @ResponseBody
    public ResultInfo dels(Integer[] ids){
        //调用方法
        roleService.deleteBatch(ids);
        //返回目标对象
        return success("删除成功");
    }

    @RequestMapping("toAddGrantPage")
    public String toAddGrantPage(Integer roleId,Model model){
        model.addAttribute("roleId",roleId);
        return "role/grant";
    }

    /**
     *
     * @param mids
     * @param roleId
     * @return
     */
    @RequestMapping("addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer[] mids,Integer roleId){
        /*System.out.println(mids);
        System.out.println(roleId);*/
        roleService.addGrant(mids,roleId);
        return success("权限添加成功");
    }
}
