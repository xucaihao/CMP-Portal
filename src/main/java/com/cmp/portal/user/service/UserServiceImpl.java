package com.cmp.portal.user.service;

import com.cmp.portal.common.CoreWsClient;
import com.cmp.portal.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * 查询用户列表
     *
     * @param user 用户
     * @return 用户列表
     */
    @Override
    public ResponseEntity<User> describeUsers(User user) {
        String url = "";
        try {
            return CoreWsClient.get(user, url, null, User.class);
        } catch (Exception e) {
            logger.error("", e);
            if (e instanceof HttpClientErrorException) {
                String msg = ((HttpClientErrorException) e).getMessage();
                throw new CmpException(msg);
            }
            throw new CmpException(e);
        }
    }
}
