package com.cmp.portal.instance.service;

import com.cmp.portal.instance.model.req.ReqCloseInstance;
import com.cmp.portal.instance.model.req.ReqStartInstance;
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
}
