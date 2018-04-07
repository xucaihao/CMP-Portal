package com.cmp.portal.cloud.controller;

import com.cmp.portal.cloud.model.req.ReqCreCloud;
import com.cmp.portal.cloud.model.req.ReqModCloud;
import com.cmp.portal.cloud.model.req.ReqModCloudType;
import com.cmp.portal.cloud.model.res.ResCloud;
import com.cmp.portal.cloud.model.res.ResCloudTypes;
import com.cmp.portal.cloud.model.res.ResClouds;
import com.cmp.portal.cloud.service.CloudService;
import com.cmp.portal.common.ResponseData;
import com.cmp.portal.common.WebUtil;
import com.cmp.portal.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Controller
@RequestMapping("")
public class CloudController {

    @Autowired
    private CloudService cloudService;

    /**
     * 获取所有可对接云平台类型
     *
     * @return 可对接云平台类型列表
     */
    @RequestMapping("/cloudTypes")
    @ResponseBody
    public ResponseData<ResCloudTypes> describeCloudTypes() {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity<ResCloudTypes> response = cloudService.describeCloudTypes(user);
            return ResponseData.build(response.getStatusCodeValue(), response.getBody());
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
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
            ResponseEntity responseEntity = cloudService.updateCloudType(user, reqModCloudType);
            return ResponseData.build(responseEntity.getStatusCodeValue(), null);
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
        }
    }

    /**
     * 获取所有已对接云平台
     *
     * @return 已对接云平台列表
     */
    @RequestMapping("/clouds")
    @ResponseBody
    public ResponseData<ResClouds> describeClouds() {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity<ResClouds> response = cloudService.describeClouds(user);
            return ResponseData.build(response.getStatusCodeValue(), response.getBody());
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
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
            return ResponseData.build(response.getStatusCodeValue(), response.getBody());
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
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
            ResponseEntity response = cloudService.createCloud(user, reqCreCloud);
            return ResponseData.build(response.getStatusCodeValue(), null);
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
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
            ResponseEntity response = cloudService.modifyCloudAttribute(user, reqModCloud, cloudId);
            return ResponseData.build(response.getStatusCodeValue(), null);
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
        }
    }

    @RequestMapping("/clouds/{cloudId}/delete")
    @ResponseBody
    public ResponseData deleteCloud(@PathVariable String cloudId) {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity response = cloudService.deleteCloud(user, cloudId);
            return ResponseData.build(response.getStatusCodeValue(), null);
        } catch (Exception e) {
            return ResponseData.failure(BAD_REQUEST.value(), e.getMessage());
        }
    }

    //测试数据 只考虑单线程操作
    public static List<CloudEntity> datas = new ArrayList<>();

    static {
        CloudEntity cloud = new CloudEntity();
        cloud.setId(1);
        cloud.setName("阿里云-1");
        cloud.setState("可用");
        cloud.setType("ali");
        datas.add(cloud);
    }

    @RequestMapping("cloudDeploy/cloudDeployListHtml")
    public ModelAndView index() {
        return new ModelAndView("pages/clouddeploy/cloud-deploy.html");
    }

    @RequestMapping("cloudDeploy/findCloudDeployList")
    @ResponseBody
    public ResData findCloudDeployList() {
        ResData resData = new ResData();
        resData.setCode(200);
        resData.setData(datas);
        return resData;
    }

    @RequestMapping("cloudDeploy/addCloudDeploy")
    @ResponseBody
    public ResData addCloudDeploy(CloudEntity cloud) {
        int id = (int) System.currentTimeMillis();
        cloud.setId(id);
        cloud.setState("可用");
        datas.add(cloud);
        ResData resData = new ResData();
        resData.setCode(201);
        return resData;
    }

    @RequestMapping("cloudDeploy/deleteCloudDeploy")
    @ResponseBody
    public ResData deleteCloudDeploy(@RequestParam(name="ids[]")List<Integer> ids) {
        for(Integer id: ids) {
            Iterator it = datas.iterator();
            while (it.hasNext()) {
                CloudEntity data = (CloudEntity) it.next();
                if (data.getId()== id) {
                    it.remove();
                    break;
                }
            }
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ResData resData = new ResData();
        resData.setCode(204);
        return resData;
    }


    @RequestMapping("cloudDeploy/modifyCloudDeploy")
    @ResponseBody
    public ResData modifyCloudDeploy(CloudEntity cloud) {
        Iterator it = datas.iterator();
        while (it.hasNext()) {
            CloudEntity data = (CloudEntity) it.next();
            if (data.getId()== cloud.getId()) {
                data = cloud;
                break;
            }
        }
        ResData resData = new ResData();
        resData.setCode(200);
        return resData;
    }
}

class ResData {
    private int code;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

class CloudEntity {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    private int id;
    private String name;
    private String state;
    private String type;
    private String ak;
    private String sk;

}