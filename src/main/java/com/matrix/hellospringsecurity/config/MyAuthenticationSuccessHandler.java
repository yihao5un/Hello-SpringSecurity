package com.matrix.hellospringsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义成功之后的处理
 *
 * @author yihaosun
 * @date 2023/3/7 21:37
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("msg", "登录成功");
        result.put("status", 200);
        // 如果想要更多信息的话 可以从authentication取到更多的用户信息
        result.put("authentication", authentication);
        response.setContentType("application/json;charset=UTF-8");
        String res = new ObjectMapper().writeValueAsString(result);
        response.getWriter().println(res);
    }
}
