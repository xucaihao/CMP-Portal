package com.cmp.portal.instance.service;

import com.cmp.portal.common.CoreWsClient;
import com.cmp.portal.common.ExceptionUtil;
import com.cmp.portal.common.JsonUtil;
import com.cmp.portal.instance.model.req.ReqCloseInstance;
import com.cmp.portal.instance.model.req.ReqModifyInstance;
import com.cmp.portal.instance.model.req.ReqRebootInstance;
import com.cmp.portal.instance.model.req.ReqStartInstance;
import com.cmp.portal.instance.model.res.ResInstance;
import com.cmp.portal.instance.model.res.ResInstances;
import com.cmp.portal.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InstanceServiceImpl implements InstanceService {

    /**
     * 查询主机列表
     *
     * @param user    用户
     * @param cloudId 云id
     * @return 主机列表
     */
    @Override
    public ResponseEntity<ResInstances> describeInstances(User user, String cloudId) {
        try {
            String url = "/instances";
            return CoreWsClient.get(user, url, cloudId, ResInstances.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 查询指定主机
     *
     * @param user       用户
     * @param cloudId    云id
     * @param regionId   区域id
     * @param instanceId 实例id
     * @return 指定主机
     */
    @Override
    public ResponseEntity<ResInstance> describeInstanceAttribute(
            User user, String cloudId, String regionId, String instanceId) {
        try {
            String url = "/" + regionId + "/instances/" + instanceId;
            return CoreWsClient.get(user, url, cloudId, ResInstance.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 关闭主机
     *
     * @param user             用户
     * @param cloudId          云id
     * @param reqCloseInstance 请求体
     * @return 操作结果
     */
    @Override
    public ResponseEntity closeInstance(User user, String cloudId, ReqCloseInstance reqCloseInstance) {
        try {
            String url = "/instances/close";
            String body = JsonUtil.objectToString(reqCloseInstance);
            return CoreWsClient.put(user, url, body, cloudId, String.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 启动主机
     *
     * @param user             用户
     * @param cloudId          云id
     * @param reqStartInstance 请求体
     * @return 操作结果
     */
    @Override
    public ResponseEntity startInstance(User user, String cloudId, ReqStartInstance reqStartInstance) {
        try {
            String url = "/instances/start";
            String body = JsonUtil.objectToString(reqStartInstance);
            return CoreWsClient.put(user, url, body, cloudId, String.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 重启主机
     *
     * @param user              用户
     * @param cloudId           云id
     * @param reqRebootInstance 请求体
     * @return 操作结果
     */
    @Override
    public ResponseEntity rebootInstance(User user, String cloudId, ReqRebootInstance reqRebootInstance) {
        try {
            String url = "/instances/reboot";
            String body = JsonUtil.objectToString(reqRebootInstance);
            return CoreWsClient.put(user, url, body, cloudId, String.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 修改主机名称
     *
     * @param user              用户
     * @param cloudId           云id
     * @param reqModifyInstance 请求体
     * @return 操作结果
     */
    @Override
    public ResponseEntity modifyInstanceName(
            User user, String cloudId, ReqModifyInstance reqModifyInstance) {
        try {
            String url = "/instances/modifyName";
            String body = JsonUtil.objectToString(reqModifyInstance);
            return CoreWsClient.put(user, url, body, cloudId, String.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

    /**
     * 重置主机密码
     *
     * @param user              用户
     * @param cloudId           云id
     * @param reqModifyInstance 请求体
     * @return 操作结果
     */
    @Override
    public ResponseEntity resetInstancesPassword(
            User user, String cloudId, ReqModifyInstance reqModifyInstance) {
        try {
            String url = "/instances/resetPassword";
            String body = JsonUtil.objectToString(reqModifyInstance);
            return CoreWsClient.put(user, url, body, cloudId, String.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }
}
