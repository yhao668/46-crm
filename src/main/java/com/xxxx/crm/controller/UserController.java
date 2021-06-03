package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.exceptions.ParamsException;
import com.xxxx.crm.modal.UserModal;
import com.xxxx.crm.query.UserQuery;
import com.xxxx.crm.service.SaleChanceService;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.SaleChance;
import com.xxxx.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {


    @Resource
    private UserService userService;


    @PostMapping("login")
    @ResponseBody
    public ResultInfo userLogin(String username,String password){
        //实例化对象
        ResultInfo result=new ResultInfo();
        //try {
            //调用方法
            /**
             * 登录成功
             *  1：存储Session,每次要启动服务，登录，
             *  2:存储Cookie
             */
            UserModal userModal = userService.userLogin(username, password);
            //登录成功
            result.setResult(userModal);
//        } catch (ParamsException e) {
//            e.printStackTrace();
//            //赋值
//            result.setCode(e.getCode());
//            result.setMsg(e.getMsg());
//        } catch (Exception e) {
//            e.printStackTrace();
//            result.setCode(300);
//            result.setMsg("操作异常");
//        }
        //返回
        return  result;
    }

    @RequestMapping("toPasswordPage")
    public String toPasswordPage(){
        return "user/password";
    }

    @PostMapping("toPasswordUpdate")
    @ResponseBody
    public ResultInfo updatePwd(HttpServletRequest req, String oldPwd, String newPwd, String confirmPwd){
        //从Cookie查询用户的id
        int userId = LoginUserUtil.releaseUserIdFromCookie(req);
        ResultInfo result= null;
        //try {
            //实例化result
            result = new ResultInfo();
            //更新
            userService.updateUserPassword(userId,oldPwd,newPwd,confirmPwd);
//        } catch (ParamsException e) {
//            e.printStackTrace();
//            result.setCode(e.getCode());
//            result.setMsg(e.getMsg());
//        } catch (Exception e) {
//            e.printStackTrace();
//            result.setCode(300);
//            result.setMsg("操作失败了");
//        }
        //返回目标对象
        return  result;
    }

    /**
     * 查询所有的销售人员
     * @return
     */
    @RequestMapping("queryAllSales")
    @ResponseBody
    public List<Map<String, Object>> queryAllSales() {
        return userService.queryAllSales();
    }

    /**
     * 多条件查询用户数据
     * @param userQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryUserByParams(UserQuery userQuery) {
        return userService.queryUserByParams(userQuery);
    }

    /**
     * 进入用户页面
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "user/user";
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateUser(User user) {
        userService.updateUser(user);
        return success("用户更新成功！");
    }

    /**
     * 进入用户添加或更新页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addOrUpdateUserPage")
    public String addUserPage(Integer id, Model model){
        if(null != id){
            model.addAttribute("user",userService.selectByPrimaryKey(id));
        }
        return "user/add_update";
    }
    /**
     * 删除用户
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer[] ids){
        userService.deleteBatch(ids);
        return success("用户记录删除成功");
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo addUser(User user){
        userService.saveUser(user);
        return success("用户记录添加成功");
    }


}
