package com.cmp.portal.disk.service;

import com.cmp.portal.common.CoreWsClient;
import com.cmp.portal.common.ExceptionUtil;
import com.cmp.portal.common.JsonUtil;
import com.cmp.portal.disk.model.req.ReqModifyDisk;
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

    /**
     * 修改硬盘名称
     *
     * @param user          用户
     * @param cloudId       云id
     * @param reqModifyDisk 请求体
     * @return 操作结果
     */
    @Override
    public ResponseEntity modifyDiskName(User user, String cloudId, ReqModifyDisk reqModifyDisk) {
        try {
            String url = "/disks/modifyName";
            String body = JsonUtil.objectToString(reqModifyDisk);
            return CoreWsClient.put(user, url, body, cloudId, String.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }
}
