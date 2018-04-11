package com.cmp.portal.region.service;

import com.cmp.portal.region.model.ResRegions;
import com.cmp.portal.user.model.User;
import org.springframework.http.ResponseEntity;

public interface RegionService {

    /**
     * 查询地域列表
     *
     * @param user 用户
     * @return 地域列表
     */
    ResponseEntity<ResRegions> describeRegions(User user);
}
