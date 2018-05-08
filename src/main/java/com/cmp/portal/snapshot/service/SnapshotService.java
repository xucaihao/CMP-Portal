package com.cmp.portal.snapshot.service;

import com.cmp.portal.snapshot.model.req.ReqCreSnapshot;
import com.cmp.portal.snapshot.model.res.ResSnapshots;
import com.cmp.portal.user.model.User;
import org.springframework.http.ResponseEntity;

public interface SnapshotService {

    /**
     * 查询快照列表
     *
     * @param user    用户
     * @param cloudId 云id
     * @return 快照列表
     */
    ResponseEntity<ResSnapshots> describeSnapshots(User user, String cloudId);

    /**
     * 创建快照
     *
     * @param user           用户
     * @param cloudId        云id
     * @param reqCreSnapshot 请求体
     * @return 操作结果
     */
    ResponseEntity createSnapshot(User user, String cloudId, ReqCreSnapshot reqCreSnapshot);
}
