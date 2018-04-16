package com.cmp.portal.image.service;

import com.cmp.portal.image.model.res.ResImages;
import com.cmp.portal.user.model.User;
import org.springframework.http.ResponseEntity;

public interface ImageService {

    /**
     * 查询镜像列表
     *
     * @param user    用户
     * @param cloudId 云id
     * @return 镜像列表
     */
    ResponseEntity<ResImages> describeImages(User user, String cloudId);
}
