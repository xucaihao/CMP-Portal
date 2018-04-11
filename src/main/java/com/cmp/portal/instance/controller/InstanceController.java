package com.cmp.portal.instance.controller;

import com.cmp.portal.common.ResponseData;
import com.cmp.portal.common.WebUtil;
import com.cmp.portal.instance.model.res.ResInstances;
import com.cmp.portal.instance.service.InstanceService;
import com.cmp.portal.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("")
public class InstanceController {

    private final InstanceService instanceService;

    @Autowired
    public InstanceController(InstanceService instanceService) {
        this.instanceService = instanceService;
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

}
