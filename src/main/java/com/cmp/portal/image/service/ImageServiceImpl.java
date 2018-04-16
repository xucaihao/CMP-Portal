package com.cmp.portal.image.service;

import com.cmp.portal.common.CoreWsClient;
import com.cmp.portal.common.ExceptionUtil;
import com.cmp.portal.image.model.res.ResImages;
import com.cmp.portal.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    /**
     * 查询镜像列表
     *
     * @param user    用户
     * @param cloudId 云id
     * @return 镜像列表
     */
    @Override
    public ResponseEntity<ResImages> describeImages(User user, String cloudId) {
        try {
            String url = "/images";
            return CoreWsClient.get(user, url, cloudId, ResImages.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }
}
