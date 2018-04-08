package com.cmp.portal.user.controller;

import com.cmp.portal.common.MySessionListener;
import com.cmp.portal.common.ResponseData;
import com.cmp.portal.common.WebUtil;
import com.cmp.portal.user.model.User;
import com.cmp.portal.user.model.req.ReqUser;
import com.cmp.portal.user.model.res.ResUser;
import com.cmp.portal.user.model.res.ResUsers;
import com.cmp.portal.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到登录页面
     *
     * @return 登录页面
     */
    @RequestMapping("")
    public ModelAndView index() {
        return new ModelAndView("login.html");
    }

    @RequestMapping("index")
    public ModelAndView toIndexPage() {
        return new ModelAndView("index.html");
    }

    /**
     * 获取在线用户列表
     *
     * @return 在线用户列表
     */
    @RequestMapping("/onlineUser")
    public ResponseData<ResUsers> describeOnlineUser() {
        MySessionListener listener = new MySessionListener();
        List<User> onlineUsers = listener.getOnlineUsers();
        return ResponseData.build(OK.value(), new ResUsers(onlineUsers));
    }

    /**
     * 用户登录
     *
     * @param account  用户账号
     * @param password 密码
     * @return 成功页面/失败页面
     */
    @RequestMapping("/login")
    public ModelAndView login(String account, String password) {
        try {
            ReqUser user = new ReqUser();
            user.setUserName(account);
            user.setPassword(password);
            ResUser loginUser = userService.describeLoginUser(user).getBody();
            MySessionListener listener = new MySessionListener();
            listener.setUser(loginUser.getUser());
            HttpSession session = WebUtil.session();
            session.setAttribute("user", loginUser.getUser());
            return new ModelAndView("index.html");
        } catch (Exception e) {
            return new ModelAndView("error.html");
        }
    }

    /**
     * 用户登出
     *
     * @return 登录页面/失败页面
     */
    public ModelAndView logout() {
        try {
            HttpSession session = WebUtil.session();
            session.invalidate();
            return new ModelAndView("login.html");
        } catch (Exception e) {
            return new ModelAndView("error.html");
        }
    }

    /**
     * 注册用户
     *
     * @param user 用户
     * @return 操作结果
     */
    @RequestMapping(value = "/users/register")
    @ResponseBody
    public ResponseData registerUser(ReqUser user) {
        try {
            ResponseEntity responseEntity = userService.registerUser(user);
            return ResponseData.build(responseEntity.getStatusCodeValue(), null);
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
        }
    }

    /**
     * 修改用户
     *
     * @param reqUser 用户
     * @return 操作结果
     */
    @RequestMapping(value = "/users/{userId}/update")
    @ResponseBody
    public ResponseData updateUser(ReqUser reqUser, @PathVariable String userId) {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity<ResUser> response = userService.updateUser(user, reqUser, userId);
            ResUser resUser = response.getBody();
            if (userId.equals(user.getUserId())) {
                HttpSession session = WebUtil.session();
                session.setAttribute("user", resUser.getUser());
            }
            return ResponseData.build(response.getStatusCodeValue(), null);
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
        }
    }

    /**
     * 查询用户列表
     *
     * @return 用户列表
     */
    @RequestMapping("/users")
    @ResponseBody
    public ResponseData<ResUsers> describeUsers() {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity<ResUsers> response = userService.describeUsers(user);
            return ResponseData.build(response.getStatusCodeValue(), response.getBody());
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
        }
    }

    /**
     * 根据id查询指定用户
     *
     * @param userId 用户id
     * @return 指定用户
     */
    @RequestMapping("/users/{userId}")
    @ResponseBody
    public ResponseData<ResUser> describeUserAttribute(@PathVariable String userId) {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity<ResUser> response = userService.describeUserAttribute(user, userId);
            return ResponseData.build(response.getStatusCodeValue(), response.getBody());
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
        }
    }

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return 操作结果
     */
    @RequestMapping("/users/{userId}/delete")
    @ResponseBody
    public ResponseData deleteUser(@PathVariable String userId) {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity response = userService.deleteUser(user, userId);
            return ResponseData.build(response.getStatusCodeValue(), null);
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
        }
    }

}
