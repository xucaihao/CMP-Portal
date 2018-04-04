package com.cmp.portal.common;

import com.cmp.portal.user.model.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WebUtil {

    /**
     * 获取request
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest request() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取response
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse response() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static HttpSession session() {
        return request().getSession();
    }

    private static Object currentSessionValue(String key) {
        return session().getAttribute(key);
    }

    public static User getCurrentUser() {
        User user = (User) currentSessionValue("user");
        user.setLocalIp(request().getRemoteAddr());
        return user;
    }

}
