package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.service.SaleChanceService;

import com.xxxx.crm.utils.CookieUtil;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.SaleChance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Autowired
    private SaleChanceService saleChanceService;



//    @RequestMapping("list")
//    @ResponseBody
//    public Map<String, Object> sayList(SaleChanceQuery query) {
//        //根据条件查询对象信息
//        Map<String, Object> map = saleChanceService.queryParam(query);
//        //返回目标map
//        return map;
//    }

    /**
     * 多条件分页查询营销机会
     * @param query
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> querySaleChanceByParams (SaleChanceQuery query, Integer flag,
                                                        HttpServletRequest request) {
        // 查询参数 flag=1 代表当前查询为开发计划数据，设置查询分配人参数
        if (null != flag && flag == 1) {
            // 获取当前登录用户的ID
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
            query.setAggsinMan(userId);
        }
        Map<String, Object> map = saleChanceService.queryParam(query);
        return map;
    }
    @RequestMapping("index")
    public String saySale() {
        return "saleChance/sale_chance";
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo save(HttpServletRequest req, SaleChance saleChance) {
//        //获取用户的UserId
        //int userId = LoginUserUtil.releaseUserIdFromCookie(req);
//        //获取登录对象
        //User user = userService.selectByPrimaryKey(userId);
        String trueName = CookieUtil.getCookieValue(req, "trueName");
        //指定销售机会的创办人
        saleChance.setCreateMan(trueName);
        //添加
        saleChanceService.insertSelective(saleChance);
        //构建resultInfo返回
        return success("营销机会添加成功了");
    }

    //更新页面有数据的显示，根据id查询用户信息
    @RequestMapping("addOrUpdateSaleChancePage")
    public String sayAddAndUpdate(Integer id, Model modal){
        // 如果id不为空，表示是修改操作，修改操作需要查询被修改的数据
        if (null != id) {
            // 通过主键查询营销机会数据
            SaleChance saleChance = saleChanceService.selectByPrimaryKey(id);
            // 将数据存到作用域中
            modal.addAttribute("saleChance", saleChance);
        }
        return "saleChance/add_update";
    }

    /**
     * 更新营销机会数据
     * @param request
     * @param saleChance
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateSaleChance(HttpServletRequest request, SaleChance saleChance){
        // 更新营销机会的数据
        saleChanceService.updateSaleChance(saleChance);
        return success("营销机会数据更新成功！");
    }

    /**
     * 删除营销机会数据
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteSaleChance (Integer[] ids) {
        // 删除营销机会的数据
        saleChanceService.deleteBatch(ids);
        return success("营销机会数据删除成功！");
    }

    /**
     * 更新营销机会的开发状态
     * @param id
     * @param devResult
     * @return
     */
    @RequestMapping("updateSaleChanceDevResult")
    @ResponseBody
    public ResultInfo updateSaleChanceDevResult(Integer id,Integer devResult){
        saleChanceService.updateSaleChanceDevResult(id,devResult);
        return success("开发状态更新成功！");
    }
}
