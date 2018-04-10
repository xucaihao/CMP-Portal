package com.cmp.portal.cloud.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * 与配置文件config/core_endpoint.properties绑定，读取、设置core服务地址
 */
@Component
@PropertySource({"classpath:config/core_endpoint.properties"})
@ConfigurationProperties(prefix = "core")
public class CoreEndPoint {

    private static final Logger logger = LoggerFactory.getLogger(CoreEndPoint.class);

    private String endpoint;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public boolean updateEndpoint(String endpoint) {
        boolean flag = true;
        BufferedWriter buff = null;
        FileWriter fw = null;
        try {
            String path = "src/main/resources/config/core_endpoint.properties";
            fw = new FileWriter(new File(path));
            buff = new BufferedWriter(fw);
            buff.write("core.endpoint=" + endpoint);
            this.setEndpoint(endpoint);
            buff.flush();
        } catch (Exception e) {
            logger.error("write file error: {}", e);
            flag = false;
        } finally {
            try {
                if (null != fw && null != buff) {
                    fw.close();
                    buff.close();
                }
            } catch (Exception e) {
                logger.error("close buff error: {}", e);
            }
        }
        return flag;
    }
}
