package com.cmp.portal.instance.controller;

import com.cmp.portal.common.ResponseData;
import com.cmp.portal.common.WebUtil;
import com.cmp.portal.instance.model.res.ResInstances;
import com.cmp.portal.instance.service.InstanceService;
import com.cmp.portal.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class InstanceController {

    private final InstanceService instanceService;

    private static final Logger logger = LoggerFactory.getLogger(InstanceController.class);

    @Autowired
    public InstanceController(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @RequestMapping("/instancesPage")
    public ModelAndView instancesPage() {
        return new ModelAndView("pages/instance/instance-list.html");
    }

    @RequestMapping("/instanceDetailPage")
    public ModelAndView instanceDetailPage() {
        return new ModelAndView("pages/instance/instance-detail.html");
    }

    @RequestMapping("/instances")
    @ResponseBody
    public ResponseData<ResInstances> describeInstances(@RequestParam(required = false) String cloudId) {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity<ResInstances> response = instanceService.describeInstances(user, cloudId);
            return ResponseData.success(response.getBody());
        } catch (Exception e) {
            return ResponseData.failure(e.getMessage());
        }
    }

    @RequestMapping("/instances/{instanceId}")
    @ResponseBody
    public ResponseData<ResInstances> describeInstance(@PathVariable String instanceId, String cloudId) {
        try {
            logger.info("start");
            User user = WebUtil.getCurrentUser();
            ResponseEntity<ResInstances> response = instanceService.describeInstances(user, cloudId);
            long l1 = System.currentTimeMillis();
            logger.info("end");
            return ResponseData.success(response.getBody());
        } catch (Exception e) {
            return ResponseData.failure(e.getMessage());
        }
    }

}
