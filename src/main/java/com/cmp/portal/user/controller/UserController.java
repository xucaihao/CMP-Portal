package com.cmp.portal.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class UserController {

    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index.html");
    }
}
