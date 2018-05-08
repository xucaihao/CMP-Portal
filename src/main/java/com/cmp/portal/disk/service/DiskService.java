package com.cmp.portal.disk.service;

import com.cmp.portal.disk.model.res.ResDisks;
import com.cmp.portal.user.model.User;
import org.springframework.http.ResponseEntity;

public interface DiskService {

    /**
     * 查询硬盘列表
     *
     * @param user    用户
     * @param cloudId 云id
     * @return 硬盘列表
     */
    ResponseEntity<ResDisks> describeDisks(User user, String cloudId);
}
