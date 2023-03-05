package com.matrix.hellospringsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yihaosun
 * @date 2023/3/4 11:12
 */
@RestController
public class SpringSecurityController {
    @RequestMapping("/hello")
    public String hello() {
        System.out.println("111");
        return "111";
    }
}
