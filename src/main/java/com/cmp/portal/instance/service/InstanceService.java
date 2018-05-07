package com.cmp.portal.instance.service;

import com.cmp.portal.instance.model.req.ReqCloseInstance;
import com.cmp.portal.instance.model.req.ReqModifyInstance;
import com.cmp.portal.instance.model.req.ReqRebootInstance;
import com.cmp.portal.instance.model.req.ReqStartInstance;
import com.cmp.portal.instance.model.res.ResInstance;
import com.cmp.portal.instance.model.res.ResInstances;
import com.cmp.portal.user.model.User;
import org.springframework.http.ResponseEntity;

public interface InstanceService {

    /**
     * 查询主机列表
     *
     * @param user    用户
     * @param cloudId 云id
     * @return 主机列表
     */
    ResponseEntity<ResInstances> describeInstances(User user, String cloudId);

    /**
     * 查询指定主机
     *
     * @param user       用户
     * @param cloudId    云id
     * @param regionId   区域id
     * @param instanceId 实例id
     * @return 指定主机
     */
    ResponseEntity<ResInstance> describeInstanceAttribute(
            User user, String cloudId, String regionId, String instanceId);

    /**
     * 关闭主机
     *
     * @param user             用户
     * @param cloudId          云id
     * @param reqCloseInstance 请求体
     * @return 操作结果
     */
    ResponseEntity closeInstance(User user, String cloudId, ReqCloseInstance reqCloseInstance);

    /**
     * 启动主机
     *
     * @param user             用户
     * @param cloudId          云id
     * @param reqStartInstance 请求体
     * @return 操作结果
     */
    ResponseEntity startInstance(User user, String cloudId, ReqStartInstance reqStartInstance);

    /**
     * 重启主机
     *
     * @param user              用户
     * @param cloudId           云id
     * @param reqRebootInstance 请求体
     * @return 操作结果
     */
    ResponseEntity rebootInstance(User user, String cloudId, ReqRebootInstance reqRebootInstance);

    /**
     * 修改主机名称
     *
     * @param user              用户
     * @param cloudId           云id
     * @param reqModifyInstance 请求体
     * @return 操作结果
     */
    ResponseEntity modifyInstanceName(User user, String cloudId, ReqModifyInstance reqModifyInstance);

    /**
     * 重置主机密码
     *
     * @param user              用户
     * @param cloudId           云id
     * @param reqModifyInstance 请求体
     * @return 操作结果
     */
    ResponseEntity resetInstancesPassword(User user, String cloudId, ReqModifyInstance reqModifyInstance);

}
