package com.cmp.portal.instance.controller;

import com.cmp.portal.common.ResponseData;
import com.cmp.portal.common.WebUtil;
import com.cmp.portal.instance.model.req.ReqCloseInstance;
import com.cmp.portal.instance.model.req.ReqModifyInstance;
import com.cmp.portal.instance.model.req.ReqRebootInstance;
import com.cmp.portal.instance.model.req.ReqStartInstance;
import com.cmp.portal.instance.model.res.ResInstance;
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

    @RequestMapping("/instancesCreatePage")
    public ModelAndView instancesCreatePage() {
        return new ModelAndView("pages/instance/instance-create.html");
    }

    @RequestMapping("/instances")
    @ResponseBody
    public ResponseData<ResInstances> describeInstances(@RequestParam(required = false) String cloudId) {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity<ResInstances> response = instanceService.describeInstances(user, cloudId);
            return ResponseData.success(response.getBody());
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    @RequestMapping("/instances/{instanceId}")
    @ResponseBody
    public ResponseData<ResInstance> describeInstance(
            @PathVariable String instanceId, String cloudId, String regionId) {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity<ResInstance> response =
                    instanceService.describeInstanceAttribute(user, cloudId, regionId, instanceId);
            return ResponseData.success(response.getBody());
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    @RequestMapping("/instances/close")
    @ResponseBody
    public ResponseData closeInstance(String cloudId, ReqCloseInstance reqCloseInstance) {
        try {
            User user = WebUtil.getCurrentUser();
            instanceService.closeInstance(user, cloudId, reqCloseInstance);
            return ResponseData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    @RequestMapping("/instances/start")
    @ResponseBody
    public ResponseData startInstance(String cloudId, ReqStartInstance reqStartInstance) {
        try {
            User user = WebUtil.getCurrentUser();
            instanceService.startInstance(user, cloudId, reqStartInstance);
            return ResponseData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    @RequestMapping("/instances/reboot")
    @ResponseBody
    public ResponseData rebootInstance(String cloudId, ReqRebootInstance reqRebootInstance) {
        try {
            User user = WebUtil.getCurrentUser();
            instanceService.rebootInstance(user, cloudId, reqRebootInstance);
            return ResponseData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    @RequestMapping("/instances/modifyName")
    @ResponseBody
    public ResponseData rebootInstance(String cloudId, ReqModifyInstance reqModifyInstance) {
        try {
            User user = WebUtil.getCurrentUser();
            instanceService.modifyInstanceName(user, cloudId, reqModifyInstance);
            return ResponseData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    @RequestMapping("/instances/resetPassword")
    @ResponseBody
    public ResponseData resetInstancesPassword(String cloudId, ReqModifyInstance reqModifyInstance) {
        try {
            User user = WebUtil.getCurrentUser();
            instanceService.resetInstancesPassword(user, cloudId, reqModifyInstance);
            return ResponseData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }
}
