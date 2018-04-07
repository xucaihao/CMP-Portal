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

    /**
     * http GET 方法
     *
     * @param url       url
     * @param headerMap http请求体头部
     * @param clz       返回体类型
     * @param <T>       T
     * @return 返回数据
     */
    protected static <T> ResponseEntity<T> get(String url, Map<String, String> headerMap, Class<T> clz) {
        HttpEntity<String> entity = buildHttpEntity(headerMap);
        String formatUrl = formatUrl(url, "GET", null);
        return restTemplate.exchange(formatUrl, HttpMethod.GET, entity, clz);
    }

    /**
     * http DELETE 方法
     *
     * @param url       url
     * @param headerMap http请求头部
     */
    protected static ResponseEntity delete(String url, Map<String, String> headerMap) {
        HttpEntity<String> entity = buildHttpEntity(headerMap);
        String formatUrl = formatUrl(url, "DELETE", null);
        return restTemplate.exchange(formatUrl, HttpMethod.DELETE, entity, String.class);
    }

    /**
     * http POST 方法（带返回体）
     *
     * @param url       url
     * @param body      请求体
     * @param headerMap 请求体头部
     * @param clz       返回类型
     * @param <T>       T
     * @return 返回数据
     */
    protected static <T> ResponseEntity<T> post(String url, String body, Map<String, String> headerMap, Class<T> clz) {
        HttpEntity<String> entity = buildHttpEntity(body, headerMap);
        String formatUrl = formatUrl(url, "POST", body);
        return restTemplate.exchange(formatUrl, HttpMethod.POST, entity, clz);
    }

    /**
     * http POST 方法（不带返回体）
     *
     * @param url       url
     * @param body      请求体
     * @param headerMap 请求体头部
     */
    protected static void post(String url, String body, Map<String, String> headerMap) {
        HttpEntity<String> entity = buildHttpEntity(body, headerMap);
        String formatUrl = formatUrl(url, "POST", body);
        restTemplate.exchange(formatUrl, HttpMethod.POST, entity, Void.class);
    }

    /**
     * http PUT 方法（带返回体）
     *
     * @param url       url
     * @param body      请求体
     * @param headerMap 请求体头部
     * @param clz       返回类型
     * @param <T>       T
     * @return 返回数据
     */
    protected static <T> ResponseEntity<T> put(String url, String body, Map<String, String> headerMap, Class<T> clz) {
        HttpEntity<String> entity = buildHttpEntity(body, headerMap);
        String formatUrl = formatUrl(url, "PUT", body);
        return restTemplate.exchange(formatUrl, HttpMethod.PUT, entity, clz);
    }

    /**
     * http PUT 方法（不带返回体）
     *
     * @param url       url
     * @param body      请求体
     * @param headerMap 请求体头部
     */
    protected static void put(String url, String body, Map<String, String> headerMap) {
        HttpEntity<String> entity = buildHttpEntity(body, headerMap);
        String formatUrl = formatUrl(url, "PUT", body);
        restTemplate.exchange(formatUrl, HttpMethod.PUT, entity, Void.class);
    }

    /**
     * 生成HttpEntity(设置http头部)
     *
     * @param headerMap 头部参数
     * @return HttpEntity
     */
    private static HttpEntity<String> buildHttpEntity(Map<String, String> headerMap) {
        HttpEntityUtil httpEntityUtil = new HttpEntityUtil();
        if (null != headerMap) {
            httpEntityUtil = httpEntityUtil.addHeaderAllValue(headerMap);
        }
        return httpEntityUtil.buildHttpEntity();
    }

    /**
     * 生成HttpEntity(设置http头部、请求体)
     *
     * @param body      请求体
     * @param headerMap 头部参数
     * @return HttpEntity
     */
    private static HttpEntity<String> buildHttpEntity(String body, Map<String, String> headerMap) {
        HttpEntityUtil httpEntityUtil = new HttpEntityUtil();
        if (null != headerMap) {
            httpEntityUtil = httpEntityUtil.addHeaderAllValue(headerMap);
        }
        return httpEntityUtil.buildHttpEntity(body);
    }

    /**
     * 组装http请求路径/打印日志
     *
     * @param url    url
     * @param method http方法
     * @param body   请求体
     * @return http请求路径
     */
    private static String formatUrl(String url, String method, String body) {
        String formatUrl = CORE_ENDPOINT + url;
        logger.info("{}:{}", method, formatUrl);
        if (!StringUtils.isEmpty(body)) {
            logger.info("body: {}", body);
        }
        return formatUrl;
    }

}
