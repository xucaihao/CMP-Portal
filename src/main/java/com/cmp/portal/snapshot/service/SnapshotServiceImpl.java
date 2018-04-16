package com.cmp.portal.snapshot.service;

import com.cmp.portal.common.CoreWsClient;
import com.cmp.portal.common.ExceptionUtil;
import com.cmp.portal.snapshot.model.res.ResSnapshots;
import com.cmp.portal.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SnapshotServiceImpl implements SnapshotService {

    /**
     * 查询快照列表
     *
     * @param user    用户
     * @param cloudId 云id
     * @return 快照列表
     */
    @Override
    public ResponseEntity<ResSnapshots> describeSnapshots(User user, String cloudId) {
        try {
            String url = "/snapshots";
            return CoreWsClient.get(user, url, cloudId, ResSnapshots.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }

}
