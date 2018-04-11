package com.cmp.portal.region.service;

import com.cmp.portal.common.CoreWsClient;
import com.cmp.portal.common.ExceptionUtil;
import com.cmp.portal.region.model.ResRegions;
import com.cmp.portal.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegionServiceImpl implements RegionService {

    /**
     * 查询地域列表
     *
     * @param user 用户
     * @return 地域列表
     */
    @Override
    public ResponseEntity<ResRegions> describeRegions(User user) {
        try {
            String url = "/regions";
            return CoreWsClient.get(user, url, null, ResRegions.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }
}
