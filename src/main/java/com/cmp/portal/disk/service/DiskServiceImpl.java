package com.cmp.portal.disk.service;

import com.cmp.portal.common.CoreWsClient;
import com.cmp.portal.common.ExceptionUtil;
import com.cmp.portal.disk.model.res.ResDisks;
import com.cmp.portal.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DiskServiceImpl implements DiskService {

    /**
     * 查询硬盘列表
     *
     * @param user    用户
     * @param cloudId 云id
     * @return 硬盘列表
     */
    @Override
    public ResponseEntity<ResDisks> describeDisks(User user, String cloudId) {
        try {
            String url = "/disks";
            return CoreWsClient.get(user, url, cloudId, ResDisks.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }
}
