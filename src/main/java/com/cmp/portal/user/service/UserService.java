package com.cmp.portal.user.service;

import com.cmp.portal.user.model.User;
import com.cmp.portal.user.model.req.ReqUser;
import com.cmp.portal.user.model.res.ResUser;
import com.cmp.portal.user.model.res.ResUsers;
import org.springframework.http.ResponseEntity;

public interface UserService {

    /**
     * 获取登录用户信息
     *
     * @param user 登录信息
     * @return 用户信息
     */
    ResponseEntity<ResUser> describeLoginUser(ReqUser user);

    /**
     * 用户注册
     *
     * @param user 用户信息
     */
    ResponseEntity registerUser(ReqUser user);

    /**
     * 用户更新
     *
     * @param user    用户
     * @param reqUser 用户信息
     * @param userId  用户id
     * @return 更新后用户信息
     */
    ResponseEntity<ResUser> updateUser(User user, ReqUser reqUser, String userId);

    /**
     * 查询用户列表
     *
     * @param user 用户
     * @return 用户列表
     */
    ResponseEntity<ResUsers> describeUsers(User user);

    /**
     * 根据id查询指定用户
     *
     * @param user   用户
     * @param userId 用户id
     * @return 指定用户
     */
    ResponseEntity<ResUser> describeUserAttribute(User user, String userId);

    /**
     * 删除用户
     *
     * @param user   用户
     * @param userId 用户id
     * @return 操作结果
     */
    ResponseEntity deleteUser(User user, String userId);


}
