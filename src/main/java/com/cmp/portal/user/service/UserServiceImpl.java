package com.cmp.portal.user.service;

import com.cmp.portal.common.CoreWsClient;
import com.cmp.portal.common.ExceptionUtil;
import com.cmp.portal.common.JsonUtil;
import com.cmp.portal.user.model.User;
import com.cmp.portal.user.model.req.ReqAddMapping;
import com.cmp.portal.user.model.req.ReqModMapping;
import com.cmp.portal.user.model.req.ReqUser;
import com.cmp.portal.user.model.res.ResUser;
import com.cmp.portal.user.model.res.ResUserMappings;
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
            String url = "/users";
            String body = JsonUtil.objectToString(user);
            return CoreWsClient.post(url, body, String.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 用户更新
     *
     * @param user    用户
     * @param reqUser 用户信息
     * @param userId  用户id
     * @return 更新后用户信息
     */
    @Override
    public ResponseEntity<ResUser> updateUser(User user, ReqUser reqUser, String userId) {
        try {
            String url = "/users/" + userId;
            String body = JsonUtil.objectToString(reqUser);
            return CoreWsClient.put(user, url, body, null, ResUser.class);
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

    /**
     * 删除用户
     *
     * @param user   用户
     * @param userId 用户id
     * @return 操作结果
     */
    @Override
    public ResponseEntity deleteUser(User user, String userId) {
        try {
            String url = "/users/" + userId;
            return CoreWsClient.delete(user, url, null);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 查询用户映射列表
     *
     * @param user 用户
     * @return 用户映射列表
     */
    @Override
    public ResponseEntity<ResUserMappings> describeUserMappings(User user) {
        try {
            String url = "/users/mappings";
            return CoreWsClient.get(user, url, null, ResUserMappings.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 添加用户映射关系
     *
     * @param user    用户
     * @param mapping 请求体
     * @return 操作结果
     */
    @Override
    public ResponseEntity addUserMapping(User user, ReqAddMapping mapping) {
        try {
            String url = "/users/mappings";
            String body = JsonUtil.objectToString(mapping);
            return CoreWsClient.post(user, url, body, null, String.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 修改用户映射关系
     *
     * @param user      用户
     * @param mapping   请求体
     * @param mappingId 映射id
     * @return 操作结果
     */
    @Override
    public ResponseEntity updateUserMapping(User user, ReqModMapping mapping, String mappingId) {
        try {
            String url = "/users/mappings/" + mappingId;
            String body = JsonUtil.objectToString(mapping);
            return CoreWsClient.put(user, url, body, null, String.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 删除用户映射关系
     *
     * @param user      用户
     * @param mappingId 映射id
     * @return 操作结果
     */
    @Override
    public ResponseEntity deleteUserMappingById(User user, String mappingId) {
        try {
            String url = "/users/mappings/" + mappingId;
            return CoreWsClient.delete(user, url, null);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }
}
