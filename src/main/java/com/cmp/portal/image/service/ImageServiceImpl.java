package com.cmp.portal.image.service;

import com.cmp.portal.common.CoreWsClient;
import com.cmp.portal.common.ExceptionUtil;
import com.cmp.portal.common.JsonUtil;
import com.cmp.portal.image.model.req.ReqCreImage;
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

    /**
     * 创建自定义镜像
     *
     * @param user        用户
     * @param cloudId     云id
     * @param reqCreImage 请求体
     * @return 操作结果
     */
    @Override
    public ResponseEntity createImage(User user, String cloudId, ReqCreImage reqCreImage) {
        try {
            String url = "/images";
            String body = JsonUtil.objectToString(reqCreImage);
            return CoreWsClient.post(user, url, body, cloudId, String.class);
        } catch (Exception e) {
            ExceptionUtil.dealThrowable(e);
            return null;
        }
    }
}
