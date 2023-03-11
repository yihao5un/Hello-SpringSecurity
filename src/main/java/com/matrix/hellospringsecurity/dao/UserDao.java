package com.matrix.hellospringsecurity.dao;

import com.matrix.hellospringsecurity.entity.Role;
import com.matrix.hellospringsecurity.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yihaosun
 * @date 2023/3/11 15:05
 */
@Mapper
public interface UserDao {
    User loadUserByUsername(String username);

    List<Role> getRolesByUid(Integer uid);
}
