package com.matrix.hellospringsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Controller 涉及到跳转所以不能用 @RestController
 *
 * @author yihaosun
 * @date 2023/3/6 22:20
 */
@Controller
public class LoginController {
    @RequestMapping("/login.html")
    public String login() {
        // 会去template下找login的html
        return "login";
    }
}
