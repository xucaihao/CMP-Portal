package com.cmp.portal.cloud.controller;

import com.cmp.portal.cloud.model.CoreEndPoint;
import com.cmp.portal.cloud.model.req.ReqCreCloud;
import com.cmp.portal.cloud.model.req.ReqModCloud;
import com.cmp.portal.cloud.model.req.ReqModCloudAdapter;
import com.cmp.portal.cloud.model.req.ReqModCloudType;
import com.cmp.portal.cloud.model.res.*;
import com.cmp.portal.cloud.service.CloudService;
import com.cmp.portal.common.ResponseData;
import com.cmp.portal.common.WebUtil;
import com.cmp.portal.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.cmp.portal.common.Constance.TIME_OUT_SECONDS;
import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("")
public class CloudController {

    private static final Logger logger = LoggerFactory.getLogger(CloudController.class);

    @Autowired
    private CloudService cloudService;

    @Autowired
    private CoreEndPoint coreEndPoint;

    /**
     * 获取所有可对接云平台类型
     *
     * @param visibility 可见类型
     * @return 可对接云平台类型列表
     */
    @RequestMapping("/cloudTypes")
    @ResponseBody
    public ResponseData<ResCloudTypes> describeCloudTypes(@RequestParam(required = false) String visibility) {
        try {
            User user = WebUtil.getCurrentUser();
            ResCloudTypes cloudTypes = cloudService.describeCloudTypes(user).getBody();
            //根据可见类型筛选
            if (!StringUtils.isEmpty(visibility)) {
                List<CloudTypeEntity> resCloudTypes = cloudTypes.getCloudTypes().stream()
                        .filter(cloudType -> visibility.equals(cloudType.getVisibility()))
                        .collect(toList());
                cloudTypes.setCloudTypes(resCloudTypes);
            }
            return ResponseData.success(cloudTypes);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    /**
     * 更新云类型（置为可用、不可用）
     *
     * @param reqModCloudType 请求体
     * @return 操作结果
     */
    @RequestMapping("/cloudTypes/update")
    @ResponseBody
    public ResponseData updateCloudType(ReqModCloudType reqModCloudType) {
        try {
            User user = WebUtil.getCurrentUser();
            cloudService.updateCloudType(user, reqModCloudType);
            return ResponseData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    /**
     * 获取所有已对接云平台
     *
     * @param visibility 可见类型
     * @return 已对接云平台列表
     */
    @RequestMapping("/clouds")
    @ResponseBody
    public ResponseData<ResClouds> describeClouds(@RequestParam(required = false) String visibility) {
        try {
            User user = WebUtil.getCurrentUser();
            ResClouds clouds = cloudService.describeClouds(user).getBody();
            //根据可见类型筛选
            if (!StringUtils.isEmpty(visibility)) {
                List<CloudEntity> resClouds = clouds.getClouds().stream()
                        .filter(cloud -> visibility.equals(cloud.getVisibility()))
                        .collect(toList());
                clouds.setClouds(resClouds);
            }
            return ResponseData.success(clouds);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    /**
     * 通过id查询指定云平台
     *
     * @param cloudId 云平台id
     * @return 指定云平台
     */
    @RequestMapping("/clouds/{cloudId}")
    @ResponseBody
    public ResponseData<ResCloud> describeCloudAttribute(@PathVariable String cloudId) {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity<ResCloud> response = cloudService.describeCloudAttribute(user, cloudId);
            return ResponseData.success(response.getBody());
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    /**
     * 纳管云
     *
     * @param reqCreCloud 请求体
     * @return 操作结果
     */
    @RequestMapping("/clouds/create")
    @ResponseBody
    public ResponseData createCloud(ReqCreCloud reqCreCloud) {
        try {
            User user = WebUtil.getCurrentUser();
            cloudService.createCloud(user, reqCreCloud);
            return ResponseData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    /**
     * 修改云平台
     *
     * @param reqModCloud 请求体
     * @param cloudId     云id
     * @return 操作结果
     */
    @RequestMapping("/clouds/{cloudId}/update")
    @ResponseBody
    public ResponseData modifyCloudAttribute(ReqModCloud reqModCloud, @PathVariable String cloudId) {
        try {
            User user = WebUtil.getCurrentUser();
            cloudService.modifyCloudAttribute(user, reqModCloud, cloudId);
            return ResponseData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

//    /**
//     * 删除云
//     *
//     * @param cloudId 云id
//     * @return 操作结果
//     */
//    @RequestMapping("/clouds/{cloudId}/delete")
//    @ResponseBody
//    public ResponseData deleteCloud(@PathVariable String cloudId) {
//        try {
//            User user = WebUtil.getCurrentUser();
//            cloudService.deleteCloud(user, cloudId);
//            return ResponseData.success();
//        } catch (Exception e) {
//            return ResponseData.failure(e.getMessage());
//        }
//    }

    /**
     * 批量删除云
     *
     * @param cloudIds 删除云id列表
     * @return 操作结果
     */
    @RequestMapping("/clouds/delete")
    @ResponseBody
    public ResponseData deleteClouds(@RequestParam("cloudIds[]") List<String> cloudIds) {
        try {
            User user = WebUtil.getCurrentUser();
            List<CompletableFuture<Map<String, String>>> futures = cloudIds.stream().map(cloudId ->
                    CompletableFuture.supplyAsync(() -> {
                        Map<String, String> response = new HashMap<>(16);
                        try {
                            cloudService.deleteCloud(user, cloudId);
                            response.put("code", "success");
                            response.put("msg", "");
                            return response;
                        } catch (Exception e) {
                            response.put("code", "fail");
                            response.put("msg", e.getMessage());
                            return response;
                        }
                    })).collect(toList());
            List<Map<String, String>> failedDel = futures.stream().map(vo -> {
                try {
                    return vo.get(TIME_OUT_SECONDS, TimeUnit.SECONDS);
                } catch (Exception e) {
                    return null;
                }
            }).filter(x -> null != x && "fail".equals(x.get("code"))).collect(toList());
            int delNum = cloudIds.size();
            int failedNum = failedDel.size();
            int successNum = delNum - failedNum;
            Map<String, Object> resMap = new HashMap<>(16);
            //删除一朵云失败，直接返回失败信息
            if (1 == delNum && 1 == failedNum) {
                return ResponseData.failure(failedDel.get(0).get("msg"));
            } else {
                resMap.put("successNum", successNum);
                resMap.put("failNum", failedNum);
                //全部删除成功
                if (delNum == successNum) {
                    return ResponseData.success(resMap);
                } else if (delNum == failedNum) {
                    //删除多朵云，全部删除失败
                    return ResponseData.failure(resMap);
                } else {
                    //删除多朵云，部分成功
                    return ResponseData.warning(resMap);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    /**
     * 查询云适配组件列表
     *
     * @return 云适配组件列表
     */
    @RequestMapping("/cloudAdapters")
    @ResponseBody
    public ResponseData<ResCloudAdapters> describeCloudAdapters() {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity<ResCloudAdapters> response = cloudService.describeCloudAdapters(user);
            return ResponseData.success(response.getBody());
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    /**
     * 更新适配组件路由地址
     *
     * @param reqModCloudAdapter 请求体
     * @return 操作结果
     */
    @RequestMapping("/cloudAdapters/update")
    @ResponseBody
    public ResponseData updateCloudAdapter(ReqModCloudAdapter reqModCloudAdapter) {
        try {
            User user = WebUtil.getCurrentUser();
            cloudService.updateCloudAdapter(user, reqModCloudAdapter);
            return ResponseData.success();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }

    /**
     * 修改core服务地址
     *
     * @param ip   ip
     * @param port 端口号
     * @return 操作结果
     */
    @RequestMapping("/coreEndpoint/update")
    @ResponseBody
    public ResponseData updateCoreEndpoint(String ip, String port) {
        String endpoint = "http://" + ip + ":" + port;
        boolean flag = coreEndPoint.updateEndpoint(endpoint);
        if (flag) {
            return ResponseData.success();
        } else {
            return ResponseData.failure();
        }
    }

    /**
     * 获取core服务地址
     *
     * @return core服务地址
     */
    @RequestMapping("/coreEndpoint")
    @ResponseBody
    public ResponseData getCoreEndpoint() {
        return ResponseData.success(coreEndPoint);
    }

//    //测试数据 只考虑单线程操作
//    public static List<CloudEntity> datas = new ArrayList<>();
//
//    static {
//        CloudEntity cloud = new CloudEntity();
//        cloud.setId(1);
//        cloud.setName("阿里云-1");
//        cloud.setState("可用");
//        cloud.setType("ali");
//        datas.add(cloud);
//    }

    @RequestMapping("cloudDeploy/cloudDeployListHtml")
    public ModelAndView index() {
        return new ModelAndView("pages/clouddeploy/cloud-deploy.html");
    }

    @RequestMapping("cloudDeploy/cloudTypeListHtml")
    public ModelAndView cloudTypeListHtml() {
        return new ModelAndView("pages/clouddeploy/cloud-type.html");
    }

    @RequestMapping("cloudDeploy/cloudAdapterListHtml")
    public ModelAndView cloudAdaptereListHtml() {
        return new ModelAndView("pages/clouddeploy/cloud-adapter.html");
    }

//    @RequestMapping("cloudDeploy/findCloudDeployList")
//    @ResponseBody
//    public ResData findCloudDeployList() {
//        ResData resData = new ResData();
//        resData.setCode(200);
//        resData.setData(datas);
//        return resData;
//    }
//
//    @RequestMapping("cloudDeploy/addCloudDeploy")
//    @ResponseBody
//    public ResData addCloudDeploy(CloudEntity cloud) {
//        int id = (int) System.currentTimeMillis();
//        cloud.setId(id);
//        cloud.setState("可用");
//        datas.add(cloud);
//        ResData resData = new ResData();
//        resData.setCode(201);
//        return resData;
//    }
//
//    @RequestMapping("cloudDeploy/deleteCloudDeploy")
//    @ResponseBody
//    public ResData deleteCloudDeploy(@RequestParam(name = "ids[]") List<Integer> ids) {
//        for (Integer id : ids) {
//            Iterator it = datas.iterator();
//            while (it.hasNext()) {
//                CloudEntity data = (CloudEntity) it.next();
//                if (data.getId() == id) {
//                    it.remove();
//                    break;
//                }
//            }
//        }
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        ResData resData = new ResData();
//        resData.setCode(204);
//        return resData;
//    }
//
//
//    @RequestMapping("cloudDeploy/modifyCloudDeploy")
//    @ResponseBody
//    public ResData modifyCloudDeploy(CloudEntity cloud) {
//        Iterator it = datas.iterator();
//        while (it.hasNext()) {
//            CloudEntity data = (CloudEntity) it.next();
//            if (data.getId() == cloud.getId()) {
//                data = cloud;
//                break;
//            }
//        }
//        ResData resData = new ResData();
//        resData.setCode(200);
//        return resData;
//    }
//}
//
//class ResData {
//    private int code;
//    private Object data;
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public Object getData() {
//        return data;
//    }
//
//    public void setData(Object data) {
//        this.data = data;
//    }
//}
//
//class CloudEntity {
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getAk() {
//        return ak;
//    }
//
//    public void setAk(String ak) {
//        this.ak = ak;
//    }
//
//    public String getSk() {
//        return sk;
//    }
//
//    public void setSk(String sk) {
//        this.sk = sk;
//    }
//
//    private int id;
//    private String name;
//    private String state;
//    private String type;
//    private String ak;
//    private String sk;

}
