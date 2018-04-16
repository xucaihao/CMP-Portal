package com.cmp.portal.volume.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class VolumeController {

    @RequestMapping("volumeDetailPage")
    public ModelAndView volumeDetailPage() {
        return new ModelAndView("pages/volume/volume-detail.html");
    }

}
