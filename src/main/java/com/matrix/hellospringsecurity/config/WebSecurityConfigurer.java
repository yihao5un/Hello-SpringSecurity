package com.matrix.hellospringsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

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
    private final MyUserDetailService myUserDetailService;

    public WebSecurityConfigurer(MyUserDetailService myUserDetailService) {
        this.myUserDetailService = myUserDetailService;
    }

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
                /* ============== 登录成功 ============== */
                // 前后端不分离
                //.successForwardUrl("/index") // 认证成功forward跳转的路径 但是地址栏不变 说明是forward跳转 (注意会始终跳转到指定路径)
                //.defaultSuccessUrl("/hello", true) // 认证成功之后的跳转 和( successForwardUrl 二选一) 重定向 redirect 跳转 路径会发生改变 (注意会根据上一次成功的请求进行跳转 如果第二个参数是true的话 那就会总是跳转到指定的了)
                // 前后端分离
                .successHandler(new MyAuthenticationSuccessHandler()) // 认证成功时的处理 前后端分离的
                /* ============== 登录失败 ============== */
                // 前后端不分离
                // .failureForwardUrl("/login.html") // 认证失败 forward 跳转 存在request里面
                // .failureUrl("/login.html") // 认证失败之后 redirect 跳转 存在session里面
                // 前后端分离
                .failureHandler(new MyAuthenticationFailureHandler()) // 用来自定义认证失败的处理
                /* ============== 登出 ============== */
                .and()
                .logout()
                // .logoutUrl("/logout") // 默认 -> 指定注销登录的URL GET
                .logoutRequestMatcher(new OrRequestMatcher(
                    new AntPathRequestMatcher(
                            "/aa", "GET"
                    ),
                    new AntPathRequestMatcher(
                            "/bb", "POST"
                    )
                )) // OrRequestMatcher or表示满足任何一个都行
                .invalidateHttpSession(true) // 默认 -> session会话失效
                .clearAuthentication(true) // 默认 -> 消除当前的认证信息
                // 前后端不分离
                // .logoutSuccessUrl("/login.html") // 默认 -> 注销登录成功后跳转的页面
                // 前后端分离
                .logoutSuccessHandler(new MyLogoutSuccessHandler())
                .and().csrf().disable(); // 暂时把跨站请求攻击关闭
    }

    // ========================== 数据源 ================================== //
    // 1. 默认 AuthenticationManager
    // springboot对security默认配置 在工厂中默认创建AuthenticationManager
    // @Autowired
    // public void initialize(AuthenticationManagerBuilder builder) {
    //    System.out.println("springboot 默认配置 AuthenticationManager:" + builder);
    // }

    // 2. 自定义 AuthenticationManager (更加推荐)
    // 工厂内部本地的AuthenticationManager对象(默认没有暴露到工厂外面,不允许在其他自定义组件中注入Autowired)
    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        System.out.println("springboot 自定义 AuthenticationManager:" + builder);
        // 手动赋值
        // builder.userDetailsService(userDetailsService()); // 默认 daoAuthenticationProvider
        // 数据库的数据源
        builder.userDetailsService(myUserDetailService);
    }

    // 通过重写这个方法可以暴露到工厂外面 并任何位置注入
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 内存实现
    // 1. 默认的AuthenticationManager -> 不用重写initialize方法了 默认就把这个UserDetailsService赋值给AuthenticationManagerBuilder了
    // 2. 自定义的AuthenticationManager ->  需要手动去重写configure方法 并需要手动将UserDetailsService赋值
//    @Bean
//    public UserDetailsService userDetailsService(){
//        // 内存实现 会将配置文件中的配置给覆盖掉只能通过aaa 和 123 进行登录
//        InMemoryUserDetailsManager inMemoryUserDetailsManager
//                = new InMemoryUserDetailsManager();
//        UserDetails u1 = User.withUsername("aaa")
//                .password("{noop}123").roles("ADMIN").build();
//        inMemoryUserDetailsManager.createUser(u1);
//        return inMemoryUserDetailsManager;
//    }
}
