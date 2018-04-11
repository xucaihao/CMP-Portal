package com.cmp.portal.instance.service;

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
}
