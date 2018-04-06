package com.cmp.portal.user.service;

import com.cmp.portal.common.CoreWsClient;
import com.cmp.portal.common.ExceptionUtil;
import com.cmp.portal.common.JsonUtil;
import com.cmp.portal.user.model.User;
import com.cmp.portal.user.model.req.ReqUser;
import com.cmp.portal.user.model.res.ResUser;
import com.cmp.portal.user.model.res.ResUsers;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    /**
     * 获取登录用户信息
     *
     * @param user 登录信息
     * @return 用户信息
     */
    @Override
    public ResponseEntity<ResUser> describeLoginUser(ReqUser user) {
        try {
            String url = "/users/loginInformation";
            String body = JsonUtil.objectToString(user);
            return CoreWsClient.post(url, body, ResUser.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 用户注册
     *
     * @param user 用户信息
     */
    @Override
    public ResponseEntity registerUser(ReqUser user) {
        try {
//            user.setUserName("xu");
//            user.setPassword("123");
//            user.setEmail("qq");
//            user.setPhone("111");
            String url = "/users";
            String body = JsonUtil.objectToString(user);
            ResponseEntity<String> post = CoreWsClient.post(url, body, String.class);
            return post;
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 查询用户列表
     *
     * @param user 用户
     * @return 用户列表
     */
    @Override
    public ResponseEntity<ResUsers> describeUsers(User user) {
        try {
            String url = "/users";
            return CoreWsClient.get(user, url, null, ResUsers.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 根据id查询指定用户
     *
     * @param user   用户
     * @param userId 用户id
     * @return 指定用户
     */
    @Override
    public ResponseEntity<ResUser> describeUserAttribute(User user, String userId) {
        try {
            String url = "/users/" + userId;
            return CoreWsClient.get(user, url, null, ResUser.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

}
