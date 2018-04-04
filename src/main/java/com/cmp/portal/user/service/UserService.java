package com.cmp.portal.user.service;

import com.cmp.portal.user.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    /**
     * 查询用户列表
     *
     * @param user 用户
     * @return 用户列表
     */
    ResponseEntity<List<User>> describeUsers(User user);
}
