package com.xxxx.crm.proxy;

import com.xxxx.crm.annotaions.RequirePermission;
import com.xxxx.crm.exceptions.NoLoginException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;

@Component
@Aspect
/**
 * todo 后端方法级别访问控制
 * 实现了菜单级别显示控制，但最终客户端有可能会通过浏览器来输入资源地址从而越过ui界面来访问后端资源，
 *  * 所以接下来加入控制方法级别资源的访问控制操作，这里使用aop+自定义注解实现
 */
public class PermissionProxy {

    @Autowired
    private HttpSession session;

    @Around(value = "@annotation(com.xxxx.crm.annotaions.RequirePermission)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        List<String> permissions = (List<String>) session.getAttribute("permissions");
        if (permissions == null || permissions.size() == 0) {
            throw new NoLoginException();
        }
        Object result = null;
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        RequirePermission requirePermission = methodSignature.getMethod().getDeclaredAnnotation(RequirePermission.class);
        if (!(permissions.contains(requirePermission.code()))) {
            throw new NoLoginException();
        }
        result = pjp.proceed();
        return result;
    }
}
