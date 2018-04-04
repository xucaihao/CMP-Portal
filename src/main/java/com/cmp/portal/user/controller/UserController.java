package com.cmp.portal.user.controller;

import com.cmp.portal.common.WebUtil;
import com.cmp.portal.user.model.User;
import com.cmp.portal.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index.html");
    }

    public ModelAndView login(String account, String password) {
        HttpSession session = WebUtil.session();
        User user = new User();
        String token = "account: admin; password: cmp";
        user.setToken(token);
        List<User> users = userService.describeUsers(user).getBody();
        return users.stream().filter(u ->
                account.equals(u.getUserName())
                        && password.equals(u.getPassword()))
                .findAny()
                .map(v -> {
                    session.setAttribute("user", v);
                    return new ModelAndView("index.html");
                }).orElseGet(() -> new ModelAndView("error.html"));
    }

}
