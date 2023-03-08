package com.matrix.hellospringsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yihaosun
 * @date 2023/3/8 22:38
 */
@Controller
public class LogoutController {
    @RequestMapping("logout.html")
    public String logout() {
        return "logout"; // 去找template中的logout视图
    }
}
