package com.xxxx.crm.annotaions;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 自定义注解
 * 实现了菜单级别显示控制，但最终客户端有可能会通过浏览器来输入资源地址从而越过ui界面来访问后端资源，
 * 所以接下来加入控制方法级别资源的访问控制操作，这里使用aop+自定义注解实现
 */
public @interface RequirePermission {
    String code() default "";
}
