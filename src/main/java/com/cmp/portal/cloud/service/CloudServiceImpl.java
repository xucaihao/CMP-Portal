package com.cmp.portal.cloud.service;

import com.cmp.portal.cloud.model.req.ReqCreCloud;
import com.cmp.portal.cloud.model.req.ReqModCloud;
import com.cmp.portal.cloud.model.req.ReqModCloudType;
import com.cmp.portal.cloud.model.res.ResCloud;
import com.cmp.portal.cloud.model.res.ResCloudTypes;
import com.cmp.portal.cloud.model.res.ResClouds;
import com.cmp.portal.common.CoreWsClient;
import com.cmp.portal.common.ExceptionUtil;
import com.cmp.portal.common.JsonUtil;
import com.cmp.portal.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CloudServiceImpl implements CloudService {

    /**
     * 获取所有可对接云平台类型
     *
     * @param user 用户
     * @return 可对接云平台类型列表
     */
    @Override
    public ResponseEntity<ResCloudTypes> describeCloudTypes(User user) {
        try {
            String url = "/clouds/types";
            return CoreWsClient.get(user, url, null, ResCloudTypes.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 更新云类型（置为可用、不可用）
     *
     * @param user            用户
     * @param reqModCloudType 请求体
     * @return 操作结果
     */
    @Override
    public ResponseEntity updateCloudType(User user, ReqModCloudType reqModCloudType) {
        try {
            String url = "/clouds/types";
            String body = JsonUtil.objectToString(reqModCloudType);
            return CoreWsClient.put(user, url, body, null, String.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 获取所有已对接云平台
     *
     * @param user 用户
     * @return 已对接云平台列表
     */
    @Override
    public ResponseEntity<ResClouds> describeClouds(User user) {
        try {
            String url = "/clouds";
            return CoreWsClient.get(user, url, null, ResClouds.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 通过id查询指定云平台
     *
     * @param user    用户
     * @param cloudId 云平台id
     * @return 指定云平台
     */
    @Override
    public ResponseEntity<ResCloud> describeCloudAttribute(User user, String cloudId) {
        try {
            String url = "/clouds/" + cloudId;
            return CoreWsClient.get(user, url, null, ResCloud.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 纳管云
     *
     * @param user        用户
     * @param reqCreCloud 请求体
     * @return 操作结果
     */
    @Override
    public ResponseEntity createCloud(User user, ReqCreCloud reqCreCloud) {
        try {
            String url = "/clouds";
            String body = JsonUtil.objectToString(reqCreCloud);
            return CoreWsClient.post(user, url, body, null, String.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 修改云平台
     *
     * @param user        用户
     * @param reqModCloud 请求体
     * @param cloudId     云id
     * @return 操作结果
     */
    @Override
    public ResponseEntity modifyCloudAttribute(User user, ReqModCloud reqModCloud, String cloudId) {
        try {
            String url = "/clouds/" + cloudId;
            String body = JsonUtil.objectToString(reqModCloud);
            return CoreWsClient.put(user, url, body, null, String.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 删除云
     *
     * @param user    用户
     * @param cloudId 云id
     * @return 操作结果
     */
    @Override
    public ResponseEntity deleteCloud(User user, String cloudId) {
        try {
            String url = "/clouds/" + cloudId;
            return CoreWsClient.delete(user, url, null);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }
}
