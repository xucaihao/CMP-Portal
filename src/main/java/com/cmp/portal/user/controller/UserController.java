package com.cmp.portal.user.controller;

import com.cmp.portal.common.ResponseData;
import com.cmp.portal.common.WebUtil;
import com.cmp.portal.user.model.User;
import com.cmp.portal.user.model.req.ReqUser;
import com.cmp.portal.user.model.res.ResUser;
import com.cmp.portal.user.model.res.ResUsers;
import com.cmp.portal.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static org.springframework.http.HttpStatus.*;

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
            HttpSession session = WebUtil.session();
            ReqUser user = new ReqUser();
            user.setUserName(account);
            user.setPassword(password);
//            user.setUserName("admin");
//            user.setPassword("cmproot");
            ResUser loginUser = userService.describeLoginUser(user).getBody();
            session.setAttribute("user", loginUser);
            return new ModelAndView("index.html");
        } catch (Exception e) {
            return new ModelAndView("error.html");
        }
    }

    @RequestMapping(value = "/users/register")
    @ResponseBody
    public ResponseData registerUser(ReqUser user) {
        try {
            userService.registerUser(user);
            return ResponseData.build(NO_CONTENT.value(), null);
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
            ResUsers users = userService.describeUsers(user).getBody();
            return ResponseData.build(OK.value(), users);
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
            ResUser resUser = userService.describeUserAttribute(user, userId).getBody();
            return ResponseData.build(OK.value(), resUser);
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
        }
    }


}
