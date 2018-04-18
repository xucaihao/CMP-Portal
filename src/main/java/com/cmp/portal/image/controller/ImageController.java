package com.cmp.portal.image.controller;

import com.cmp.portal.common.ResponseData;
import com.cmp.portal.common.WebUtil;
import com.cmp.portal.image.model.res.ResImageInfo;
import com.cmp.portal.image.model.res.ResImages;
import com.cmp.portal.image.service.ImageService;
import com.cmp.portal.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @RequestMapping("imageListPage")
    public ModelAndView imageListPage() {
        return new ModelAndView("pages/image/image-list.html");
    }

    @RequestMapping("imageDetailPage")
    public ModelAndView imageDetailPage() {
        return new ModelAndView("pages/image/image-detail.html");
    }

    /**
     * 查询公有镜像列表
     *
     * @param cloudId 云id
     * @return 公有镜像列表
     */
    @RequestMapping("/publicImages")
    @ResponseBody
    public ResponseData<ResImages> describePublicImages(@RequestParam(required = false) String cloudId) {
        try {
            User user = WebUtil.getCurrentUser();
            ResImages images = imageService.describeImages(user, cloudId).getBody();
            List<ResImageInfo> resImages = images.getImages().stream()
                    .filter(image ->
                            image.getImageOwnerAlias().toLowerCase().contains("public")
                                    || image.getImageOwnerAlias().toLowerCase().contains("system"))
                    .collect(toList());
            return ResponseData.success(new ResImages(resImages));
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    /**
     * 查询自定义镜像列表
     *
     * @param cloudId 云id
     * @return 自定义镜像列表
     */
    @RequestMapping("/privateImages")
    @ResponseBody
    public ResponseData<ResImages> describePrivateImages(@RequestParam(required = false) String cloudId) {
        try {
            User user = WebUtil.getCurrentUser();
            ResImages images = imageService.describeImages(user, cloudId).getBody();
            List<ResImageInfo> resImages = images.getImages().stream()
                    .filter(image ->
                            image.getImageOwnerAlias().toLowerCase().contains("private")
                                    || image.getImageOwnerAlias().toLowerCase().contains("self"))
                    .collect(toList());
            return ResponseData.success(new ResImages(resImages));
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    /**
     * 查询自定义镜像列表
     *
     * @param cloudId 云id
     * @return 自定义镜像列表
     */
    @RequestMapping("/sharedImages")
    @ResponseBody
    public ResponseData<ResImages> describeSharedImages(@RequestParam(required = false) String cloudId) {
        try {
            User user = WebUtil.getCurrentUser();
            ResImages images = imageService.describeImages(user, cloudId).getBody();
            List<ResImageInfo> resImages = images.getImages().stream()
                    .filter(image ->
                            image.getImageOwnerAlias().toLowerCase().contains("shared")
                                    || image.getImageOwnerAlias().toLowerCase().contains("others"))
                    .collect(toList());
            return ResponseData.success(new ResImages(resImages));
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

}
