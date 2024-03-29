package com.matrix.hellospringsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义失败之后的处理
 *
 * @author yihaosun
 * @date 2023/3/7 22:32
 */
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("登录失败: ", exception.getMessage());
        result.put("status", 500);
        response.setContentType("application/json;charset=UTF-8");
        String res = new ObjectMapper().writeValueAsString(result);
        response.getWriter().println(res);
    }
}
