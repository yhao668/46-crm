package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.dto.TreeDto;
import com.xxxx.crm.vo.Module;

import java.util.List;

public interface ModuleMapper extends BaseMapper<Module,Integer> {

    List<TreeDto> queryAllModules();

    List<Module> queryModules();
}