package com.xxxx.crm.interceptors;

import com.xxxx.crm.exceptions.NoLoginException;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoLoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否登录cookie useIdStr,
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //
        if(null== userId || userService.selectByPrimaryKey(userId)==null){
            throw new NoLoginException();
        }
        //放行
        return true;
    }
}
