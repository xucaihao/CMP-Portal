package com.cmp.portal.floatingip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping()
public class FloatingIpController {
    @RequestMapping("floatingip/floatingIpListHtml")
    public ModelAndView index() {
        return new ModelAndView("pages/floatingip/floatingip.html");
    }

    @RequestMapping("floatingIp/findFloatingIpList")
    @ResponseBody
    public ResData findFloatingIpList() {
      List<Object> list = new ArrayList<>();
      Map<String, String> map = new HashMap<>();
      map.put("id", "1");
      list.add(map);
      ResData resData = new ResData();
      resData.setCode(200);
      resData.setData(list);
      return resData;
    };
}
class  ResData {
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