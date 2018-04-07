package com.cmp.portal.common;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static com.cmp.portal.common.Constance.HTTP_MSG_TYPE;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

public class HttpEntityUtil {

    private HttpHeaders headers = new HttpHeaders();

    /**
     * 初始换http请求头部
     */
    HttpEntityUtil() {
        headers.set(ACCEPT, HTTP_MSG_TYPE);
        headers.set(CONTENT_TYPE, HTTP_MSG_TYPE);
    }

    /**
     * 增加自定义http请求头部
     *
     * @param headerName  消息头key值
     * @param headerValue 消息头value值
     * @return this
     */
    public HttpEntityUtil addHeaderAllValue(String headerName, String headerValue) {
        headers.set(headerName, headerValue);
        return this;
    }

    /**
     * 自定义http请求头部
     *
     * @param headerMap 消息头部参数
     * @return this
     */
    public HttpEntityUtil addHeaderAllValue(Map<String, String> headerMap) {
        headerMap.forEach((headerName, headerValue) -> {
            headers.set(headerName, headerValue);
        });
        return this;
    }

    /**
     * 构建HttpEntity
     *
     * @return HttpEntity
     */
    public HttpEntity<String> buildHttpEntity() {
        return new HttpEntity<>(headers);
    }

    /**
     * 构建HttpEntity
     *
     * @param body 请求体
     * @return HttpEntity
     */
    public HttpEntity<String> buildHttpEntity(String body) {
        return new HttpEntity<>(body, headers);
    }

    /**
     * 构建HttpEntity
     *
     * @param body 请求体
     * @return HttpEntity
     */
    public HttpEntity<MultiValueMap<String, Object>> buildHttpEntity(MultiValueMap<String, Object> body) {
        return new HttpEntity<>(body, headers);
    }
}
