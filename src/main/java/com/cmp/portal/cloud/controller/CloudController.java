package com.cmp.portal.cloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("")
public class CloudController {
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
            Thread.sleep(15000);
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
