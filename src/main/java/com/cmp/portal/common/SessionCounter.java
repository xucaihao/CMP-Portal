package com.cmp.portal.common;

import com.cmp.portal.user.model.User;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 统计在线用户
 * 定义一个监听器，实现HttpSessionListener接口
 */
@WebListener
public class SessionCounter implements HttpSessionListener {

    public static List<User> onlineUsers = new ArrayList<>();

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        ServletContext application = session.getServletContext();
        // 取得登录用户
        User user = (User) session.getAttribute("user");
        // 从在线列表中删除用户名
        List<User> onlineUserList = (List<User>) application.getAttribute("onlineUserList");
        onlineUserList.remove(user);
        application.setAttribute("onlineUserList", onlineUserList);
    }
}
