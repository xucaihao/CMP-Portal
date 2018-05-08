package com.cmp.portal.disk.controller;

import com.cmp.portal.common.ResponseData;
import com.cmp.portal.common.WebUtil;
import com.cmp.portal.disk.model.res.ResDisks;
import com.cmp.portal.disk.service.DiskService;
import com.cmp.portal.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class DiskController {

    private static final Logger logger = LoggerFactory.getLogger(DiskController.class);

    @Autowired
    private DiskService diskService;

    @RequestMapping("diskListPage")
    public ModelAndView diskListPage() {
        return new ModelAndView("pages/disk/disk-list.html");
    }

    @RequestMapping("diskDetailPage")
    public ModelAndView diskDetailPage() {
        return new ModelAndView("pages/disk/disk-detail.html");
    }

    /**
     * 查询硬盘列表
     *
     * @param cloudId 云id
     * @return 硬盘列表
     */
    @RequestMapping("/disks")
    @ResponseBody
    public ResponseData<ResDisks> describeDisks(@RequestParam(required = false) String cloudId) {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity<ResDisks> response = diskService.describeDisks(user, cloudId);
            return ResponseData.success(response.getBody());
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }
}
