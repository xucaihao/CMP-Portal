package com.cmp.portal.instance.service;

import com.cmp.portal.common.CoreWsClient;
import com.cmp.portal.common.ExceptionUtil;
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
}
