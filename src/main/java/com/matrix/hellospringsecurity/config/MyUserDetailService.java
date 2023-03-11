package com.matrix.hellospringsecurity.config;

import com.matrix.hellospringsecurity.dao.UserDao;
import com.matrix.hellospringsecurity.entity.Role;
import com.matrix.hellospringsecurity.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 自定义数据源数据库实现
 * 再将此类注入到 AuthenticationService
 *
 * @author yihaosun
 * @date 2023/3/11 14:42
 */
@Component
public class MyUserDetailService implements UserDetailsService {
    private final UserDao userDao;

    public MyUserDetailService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        User user = userDao.loadUserByUsername(username);
        if (Objects.isNull(user)) throw new UsernameNotFoundException("用户名不正确");
        // 查询权限信息
        List<Role> roles = userDao.getRolesByUid(user.getId());
        user.setRoles(roles);
        return user;
    }
}
