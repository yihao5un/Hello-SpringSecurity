package com.matrix.hellospringsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yihaosun
 * @date 2023/3/4 11:12
 */
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello() {
        System.out.println("hello");
        // 1. 获取认证信息(默认是通过ThreadLocal实现的)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("身份信息: " + authentication.getPrincipal());
        System.out.println("权限信息: " + authentication.getAuthorities());
        // 验证子线程是否能拿到 ThreadLocal (不可以在子线程获取)
        // 如果想要在子线程中也获取到, 那么需要在启动类中配置 VM Options 参数
        // -Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL
        new Thread(() -> {
            Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("子线程: " + authentication1);
        }).start();
        return "hello";
    }
}
