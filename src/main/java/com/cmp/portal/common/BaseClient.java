package com.cmp.portal.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.cmp.portal.common.Constance.CORE_ENDPOINT;

class BaseClient {

    private static final Logger logger = LoggerFactory.getLogger(BaseClient.class);

    /**
     * rest服务
     */
    private static final RestTemplate restTemplate = new RestTemplate();

    static <T> ResponseEntity<T> get(String url, Map<String, String> headerMap, Class<T> clz) {
        HttpEntity<String> entity = buildHttpEntity(headerMap);
        String formatUrl = formatUrl(url, "GET", null);
        return restTemplate.exchange(formatUrl, HttpMethod.GET, entity, clz);
    }

    static void delete(String url, Map<String, String> headerMap) {
        HttpEntity<String> entity = buildHttpEntity(headerMap);
        String formatUrl = formatUrl(url, "DELETE", null);
        restTemplate.exchange(formatUrl, HttpMethod.DELETE, entity, Void.class);
    }

    static <T> ResponseEntity<T> post(String url, String body, Map<String, String> headerMap, Class<T> clz) {
        HttpEntity<String> entity = buildHttpEntity(body, headerMap);
        String formatUrl = formatUrl(url, "POST", body);
        return restTemplate.exchange(formatUrl, HttpMethod.POST, entity, clz);
    }

    static void post(String url, String body, Map<String, String> headerMap) {
        HttpEntity<String> entity = buildHttpEntity(body, headerMap);
        String formatUrl = formatUrl(url, "POST", body);
        restTemplate.exchange(formatUrl, HttpMethod.POST, entity, Void.class);
    }

    static <T> ResponseEntity<T> put(String url, String body, Map<String, String> headerMap, Class<T> clz) {
        HttpEntity<String> entity = buildHttpEntity(body, headerMap);
        String formatUrl = formatUrl(url, "PUT", body);
        return restTemplate.exchange(formatUrl, HttpMethod.PUT, entity, clz);
    }

    static void put(String url, String body, Map<String, String> headerMap) {
        HttpEntity<String> entity = buildHttpEntity(body, headerMap);
        String formatUrl = formatUrl(url, "PUT", body);
        restTemplate.exchange(formatUrl, HttpMethod.PUT, entity, Void.class);
    }

    private static HttpEntity<String> buildHttpEntity(Map<String, String> headerMap) {
        HttpEntityUtil httpEntityUtil = new HttpEntityUtil();
        if (null != headerMap) {
            httpEntityUtil = httpEntityUtil.addHeaderAllValue(headerMap);
        }
        return httpEntityUtil.buildHttpEntity();
    }

    private static HttpEntity<String> buildHttpEntity(String body, Map<String, String> headerMap) {
        HttpEntityUtil httpEntityUtil = new HttpEntityUtil();
        if (null != headerMap) {
            httpEntityUtil = httpEntityUtil.addHeaderAllValue(headerMap);
        }
        return httpEntityUtil.buildHttpEntity(body);
    }

    private static String formatUrl(String url, String method, String body) {
        String formatUrl = CORE_ENDPOINT + url;
        logger.info("{}:{}", method, formatUrl);
        if (!StringUtils.isEmpty(body)) {
            logger.info("body: {}", body);
        }
        return formatUrl;
    }

}
