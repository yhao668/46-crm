package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.dto.TreeDto;
import com.xxxx.crm.service.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {

    @Resource
    private ModuleService moduleService;

    /**
     * todo 查询所有模块已tree形式展示
     * @return
     */
    @RequestMapping("queryAllModules")
    @ResponseBody
    public List<TreeDto> queryAllModules(Integer roleId){
        return moduleService.queryAllModules2(roleId);
    }

    @RequestMapping("index")
    public String index(){
        return "module/module";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> moduleList(){
        return moduleService.moduleList();
    }
}
