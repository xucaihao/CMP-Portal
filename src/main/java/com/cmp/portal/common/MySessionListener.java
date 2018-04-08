package com.cmp.portal.common;

import com.cmp.portal.user.model.User;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 统计在线用户
 * 定义一个监听器，实现HttpSessionBindingListener接口
 */
@WebListener
public class MySessionListener implements HttpSessionBindingListener {

    private User user;
    private static List<User> onlineUsers = new ArrayList<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getOnlineUsers() {
        return onlineUsers;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
        onlineUsers.add(user);
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {
        onlineUsers.remove(user);
    }

}
