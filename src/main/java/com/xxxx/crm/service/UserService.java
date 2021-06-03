package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.UserMapper;
import com.xxxx.crm.dao.UserRoleMapper;
import com.xxxx.crm.modal.UserModal;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.query.UserQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.utils.Md5Util;
import com.xxxx.crm.utils.UserIDBase64;
import com.xxxx.crm.vo.User;
import com.xxxx.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserService extends BaseService<User, Integer> {

    @Resource
    private UserMapper userMapper;

    /**
     *  用户密码修改
     *      1. 参数校验
     *          userId  非空  用户对象必须存在
     *          oldPassword 非空  与数据库中密文密码保持一致
     *          newPassword 非空  与原始密码不能相同
     *          confirmPassword 非空  与新密码保持一致
     *      2. 设置用户新密码
     *          新密码进行加密处理
     *      3. 执行更新操作
     *          受影响的行数小于1，则表示修改失败
     */

    /**
     * @param userId      用户的id
     * @param oldPassword 原始密码
     * @param newPassword 新密码
     * @param confirmPwd  确认密码
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserPassword(Integer userId, String oldPassword, String newPassword, String confirmPwd) {
        //userId  非空  用户对象必须存在
        User user = userMapper.selectByPrimaryKey(userId);
        //参数校验
        checkUserParams(user, oldPassword, newPassword, confirmPwd);
        // 2. 设置用户新密码
        //     *          新密码进行加密处理
        user.setUserPwd(Md5Util.encode(newPassword));
        //执行更新操作
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1, "更新失败了");
    }

    /**
     * @param user
     * @param oldPassword
     * @param newPassword
     * @param confirmPwd
     */
    private void checkUserParams(User user, String oldPassword, String newPassword, String confirmPwd) {
        //  // user对象 非空验证
        AssertUtil.isTrue(null == user, "用户未登录或不存在！");
        //    // 原始密码 非空验证
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword), "请输入原始密码！");
        // 原始密码要与数据库中的密文密码保持一致
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(oldPassword))), "原始密码不正确！");
        // 新密码 非空校验
        AssertUtil.isTrue(StringUtils.isBlank(newPassword), "请输入新密码！");
        // 新密码与原始密码不能相同
        AssertUtil.isTrue(oldPassword.equals(newPassword), "新密码不能与原始密码相同！");
        // 确认密码 非空校验
        AssertUtil.isTrue(StringUtils.isBlank(confirmPwd), "请输入确认密码！");
        // 新密码要与确认密码保持一致
        AssertUtil.isTrue(!(newPassword.equals(confirmPwd)), "新密码与确认密码不一致！");
    }

    /**
     * 用户登录
     * *      1. 验证参数
     * *          姓名 非空判断
     * *          密码 非空判断
     * *      2. 根据用户名，查询用户对象
     * *      3. 判断用户是否存在
     * *          用户对象为空，记录不存在，方法结束
     * *      4. 用户对象不为空
     * *          用户存在，校验密码
     * *              密码不正确，方法结束
     * *      5. 密码正确
     * *          用户登录成功，返回用户的相关信息 （定义UserModel类，返回用户某些信息）
     */


    public UserModal userLogin(String userName, String userPwd) {
        // 1. 验证参数
        checkUserParam(userName, userPwd);
        //2. 根据用户名，查询用户对象
        User user = userMapper.selectUserByName(userName);
        //判断
        AssertUtil.isTrue(user == null, "用户不存在或已经注销");
        // 3.用户存在，校验密码
        checkUserPassword(userPwd, user.getUserPwd());
        //4.登录成功，返回userModal
        return builderUserModal(user);
    }

    /**
     * @param user
     * @return
     */
    private UserModal builderUserModal(User user) {
        //实例化目标对象
        UserModal userModal = new UserModal();
        userModal.setUserName(user.getUserName());
        userModal.setTrueName(user.getTrueName());
        userModal.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        //返回
        return userModal;
    }

    /**
     * 校验密码
     *
     * @param userPwd  新接收的密码（前端）
     * @param userPwd1 原始密码（db）
     */
    private void checkUserPassword(String userPwd, String userPwd1) {
        //加密（数据库中的密码已经加密，故此，前端输入的密码要加密，然后比较）
        userPwd = Md5Util.encode(userPwd);
        //比较
        AssertUtil.isTrue(!userPwd.equals(userPwd1), "密码输入错误");
    }

    /**
     * 校验参数
     *
     * @param userName
     * @param userPwd
     */
    private void checkUserParam(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd), "用户密码不能为空");
    }

    /**
     * 查询所有的销售人员
     * @return
     */
    public List<Map<String, Object>> queryAllSales() {
        return userMapper.queryAllSales();
    }

    /**
     * 多条件分页查询用户数据
     * @param query
     * @return
     */
    public Map<String, Object> queryUserByParams (UserQuery query) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(), query.getLimit());
        PageInfo<User> pageInfo = new PageInfo<>(userMapper.selectByParams(query));
        map.put("code",0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }


    @Resource
    private UserRoleService userRoleService;
    /**
     * 添加用户
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(User user) {
        // 1. 参数校验
        checkParams(user.getUserName(), user.getEmail(), user.getPhone());
        // 2. 设置默认参数
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("123456"));
        // 3. 执行添加，判断结果
       AssertUtil.isTrue(userMapper.insertSelective(user) == null, "用户添加失败！");

       userRoleService.relaionUserRole(user.getId(), user.getRoleIds());

    }

    private void checkParams(String userName, String email, String phone) {

        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空");
        // 验证用户名是否存在
        User temp = userMapper.queryUserByUserName(userName);
        AssertUtil.isTrue(null != temp, "该用户已存在！");
        AssertUtil.isTrue(StringUtils.isBlank(email), "邮箱不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(phone), "电话号码不能为空");
    }



    /**
     * 更新用户
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user) {

        // 1. 参数校验
        // 通过id查询用户对象
        User temp = userMapper.selectByPrimaryKey(user.getId());
//        System.out.println(temp.getUserName()+"原来");
//
//        System.out.println(user.getUserName()+"修改");
        if(!temp.getUserName().equals(user.getUserName())){
            User temp1 = userMapper.queryUserByUserName(user.getUserName());
            AssertUtil.isTrue(null != temp1, "该用户已存在！");
        }
        // 判断对象是否存在
        AssertUtil.isTrue(temp == null, "待更新记录不存在！");
        // 验证参数
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()), "用户名不能为空");

        AssertUtil.isTrue(StringUtils.isBlank(user.getEmail()), "邮箱不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(user.getPhone()), "电话号码不能为空");
        // 2. 设置默认参数
        user.setUpdateDate(new Date());
        // 3. 执行更新，判断结果
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1, "用户更新失败！");

       Integer userId = userMapper.selectUserByName(user.getUserName()).getId();
       userRoleService.relaionUserRole(userId, user.getRoleIds());

    }



}
