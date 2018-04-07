package com.cmp.portal.cloud.controller;

import com.cmp.portal.cloud.model.req.ReqCreCloud;
import com.cmp.portal.cloud.model.req.ReqModCloud;
import com.cmp.portal.cloud.model.req.ReqModCloudType;
import com.cmp.portal.cloud.model.res.ResCloud;
import com.cmp.portal.cloud.model.res.ResCloudTypes;
import com.cmp.portal.cloud.model.res.ResClouds;
import com.cmp.portal.cloud.service.CloudService;
import com.cmp.portal.common.ResponseData;
import com.cmp.portal.common.WebUtil;
import com.cmp.portal.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Controller
@RequestMapping("")
public class CloudController {

    @Autowired
    private CloudService cloudService;

    /**
     * 获取所有可对接云平台类型
     *
     * @return 可对接云平台类型列表
     */
    @RequestMapping("/cloudTypes")
    @ResponseBody
    public ResponseData<ResCloudTypes> describeCloudTypes() {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity<ResCloudTypes> response = cloudService.describeCloudTypes(user);
            return ResponseData.build(response.getStatusCodeValue(), response.getBody());
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
        }
    }

    /**
     * 更新云类型（置为可用、不可用）
     *
     * @param reqModCloudType 请求体
     * @return 操作结果
     */
    @RequestMapping("/cloudTypes/update")
    @ResponseBody
    public ResponseData updateCloudType(ReqModCloudType reqModCloudType) {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity responseEntity = cloudService.updateCloudType(user, reqModCloudType);
            return ResponseData.build(responseEntity.getStatusCodeValue(), null);
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
        }
    }

    /**
     * 获取所有已对接云平台
     *
     * @return 已对接云平台列表
     */
    @RequestMapping("/clouds")
    @ResponseBody
    public ResponseData<ResClouds> describeClouds() {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity<ResClouds> response = cloudService.describeClouds(user);
            return ResponseData.build(response.getStatusCodeValue(), response.getBody());
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
        }
    }

    /**
     * 通过id查询指定云平台
     *
     * @param cloudId 云平台id
     * @return 指定云平台
     */
    @RequestMapping("/clouds/{cloudId}")
    @ResponseBody
    public ResponseData<ResCloud> describeCloudAttribute(@PathVariable String cloudId) {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity<ResCloud> response = cloudService.describeCloudAttribute(user, cloudId);
            return ResponseData.build(response.getStatusCodeValue(), response.getBody());
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
        }
    }

    /**
     * 纳管云
     *
     * @param reqCreCloud 请求体
     * @return 操作结果
     */
    @RequestMapping("/clouds/create")
    @ResponseBody
    public ResponseData createCloud(ReqCreCloud reqCreCloud) {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity response = cloudService.createCloud(user, reqCreCloud);
            return ResponseData.build(response.getStatusCodeValue(), null);
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
        }
    }

    /**
     * 修改云平台
     *
     * @param reqModCloud 请求体
     * @param cloudId     云id
     * @return 操作结果
     */
    @RequestMapping("/clouds/{cloudId}/update")
    @ResponseBody
    public ResponseData modifyCloudAttribute(ReqModCloud reqModCloud, @PathVariable String cloudId) {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity response = cloudService.modifyCloudAttribute(user, reqModCloud, cloudId);
            return ResponseData.build(response.getStatusCodeValue(), null);
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
        }
    }

    @RequestMapping("/clouds/{cloudId}/delete")
    @ResponseBody
    public ResponseData deleteCloud(@PathVariable String cloudId) {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity response = cloudService.deleteCloud(user, cloudId);
            return ResponseData.build(response.getStatusCodeValue(), null);
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
        }
    }

}
