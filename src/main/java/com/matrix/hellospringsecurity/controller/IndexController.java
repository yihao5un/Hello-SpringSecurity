package com.matrix.hellospringsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yihaosun
 * @date 2023/3/6 22:01
 */
@RestController
public class IndexController {
    @RequestMapping("/index")
    public String index() {
        System.out.println("index");
        return "index";
    }
}
