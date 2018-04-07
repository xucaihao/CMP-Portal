package com.cmp.portal.cloud.service;

import com.cmp.portal.cloud.model.req.ReqCreCloud;
import com.cmp.portal.cloud.model.req.ReqModCloud;
import com.cmp.portal.cloud.model.req.ReqModCloudType;
import com.cmp.portal.cloud.model.res.ResCloud;
import com.cmp.portal.cloud.model.res.ResCloudTypes;
import com.cmp.portal.cloud.model.res.ResClouds;
import com.cmp.portal.user.model.User;
import org.springframework.http.ResponseEntity;

public interface CloudService {

    /**
     * 获取所有可对接云平台类型
     *
     * @param user 用户
     * @return 可对接云平台类型列表
     */
    ResponseEntity<ResCloudTypes> describeCloudTypes(User user);

    /**
     * 更新云类型（置为可用、不可用）
     *
     * @param user            用户
     * @param reqModCloudType 请求体
     * @return 操作结果
     */
    ResponseEntity updateCloudType(User user, ReqModCloudType reqModCloudType);

    /**
     * 获取所有已对接云平台
     *
     * @param user 用户
     * @return 已对接云平台列表
     */
    ResponseEntity<ResClouds> describeClouds(User user);

    /**
     * 通过id查询指定云平台
     *
     * @param user    用户
     * @param cloudId 云平台id
     * @return 指定云平台
     */
    ResponseEntity<ResCloud> describeCloudAttribute(User user, String cloudId);

    /**
     * 纳管云
     *
     * @param user        用户
     * @param reqCreCloud 请求体
     * @return 操作结果
     */
    ResponseEntity createCloud(User user, ReqCreCloud reqCreCloud);

    /**
     * 修改云平台
     *
     * @param user        用户
     * @param reqModCloud 请求体
     * @param cloudId     云id
     * @return 操作结果
     */
    ResponseEntity modifyCloudAttribute(User user, ReqModCloud reqModCloud, String cloudId);

    /**
     * 删除云
     *
     * @param user    用户
     * @param cloudId 云id
     * @return 操作结果
     */
    ResponseEntity deleteCloud(User user, String cloudId);

}
