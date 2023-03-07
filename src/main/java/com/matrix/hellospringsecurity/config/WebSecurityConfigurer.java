package com.matrix.hellospringsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 自定义一个类去代替 WebSecurityConfigurerAdapter 并重写configure方法 就不走原来的默认配置了
 * mvcMatchers 必须在 anyRequest之前 放行的资源要写在前面
 * loginPage()指定自定义登录的路径
 *
 * @author yihaosun
 * @date 2023/3/6 22:05
 */
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/login.html").permitAll()
                .mvcMatchers("/index").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html") // 覆盖掉默认的登录页面 注意: 一旦自定义登录页面以后必须只能登录URL
                .loginProcessingUrl("/doLogin") // 自定义页面必须指定登录请求的URL 之后走视图登录的方法(和 thymeleaf 里面的url一致(不需要有 名字一样就行))
                .usernameParameter("uname") // 自定义 thymeleaf 中的username参数名字为uname
                .passwordParameter("passwd") // 自定义 thymeleaf 中的password参数名字passwd
                // 下面这两个不是针对前后端分离开发的方式
                //.successForwardUrl("/index") // 认证成功forward跳转的路径 但是地址栏不变 说明是forward跳转 (注意会始终跳转到指定路径)
                //.defaultSuccessUrl("/hello", true) // 认证成功之后的跳转 和( successForwardUrl 二选一) 重定向 redirect 跳转 路径会发生改变 (注意会根据上一次成功的请求进行跳转 如果第二个参数是true的话 那就会总是跳转到指定的了)
                // 下面这个是针对于前后端分离的开发方式
                .successHandler(new MyAuthenticationSuccessHandler()) // 认证成功时的处理 前后端分离的
                // 下面这两个不是针对前后端分离开发的方式
                // .failureForwardUrl("/login.html") // 认证失败 forward 跳转 存在request里面
                // .failureUrl("/login.html") // 认证失败之后 redirect 跳转 存在session里面
                // 下面这个是针对于前后端分离的开发方式
                .failureHandler(new MyAuthenticationFailureHandler()) // 用来自定义认证失败的处理
                .and().csrf().disable(); // 暂时把跨站请求攻击关闭
    }
}
