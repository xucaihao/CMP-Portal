package com.cmp.portal.common;

import com.cmp.portal.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static com.cmp.portal.common.Constance.*;

public class CoreWsClient extends BaseClient {

    /**
     * http GET 方法
     *
     * @param user    用户
     * @param url     url
     * @param cloudId 云id(带参数则查询单云数据，不带参数则查询多云数据)
     * @param clz     返回数据类型
     * @param <T>     T
     * @return 返回数据
     */
    public static <T> ResponseEntity<T> get(User user, String url, String cloudId, Class<T> clz) {
        Map<String, String> headerMap = new HashMap<>(16);
        if (!StringUtils.isEmpty(cloudId)) {
            headerMap.put(HEADER_CLOUD_ID, cloudId);
        }
        headerMap.put(X_AUTH_TOKEN, user.getToken());
        return get(url, headerMap, clz);
    }

    /**
     * http DELETE 方法
     *
     * @param user    用户
     * @param url     url
     * @param cloudId 云id
     */
    public static ResponseEntity delete(User user, String url, String cloudId) {
        Map<String, String> headerMap = makeHeader(user, cloudId);
        return delete(url, headerMap);
    }

    /**
     * http POST 方法（header中不设置cloudId和token）
     *
     * @param url  url
     * @param body 请求体
     * @param clz  返回体类型
     * @param <T>  T
     * @return 返回数据
     */
    public static <T> ResponseEntity<T> post(String url, String body, Class<T> clz) {
        Map<String, String> headerMap = new HashMap<>(16);
        return post(url, body, headerMap, clz);
    }

    /**
     * http POST 方法（带返回体）
     *
     * @param user    用户
     * @param url     url
     * @param body    请求体
     * @param cloudId 云id
     * @param clz     返回体类型
     * @param <T>     T
     * @return 返回数据
     */
    public static <T> ResponseEntity<T> post(User user, String url, String body, String cloudId, Class<T> clz) {
        Map<String, String> headerMap = makeHeader(user, cloudId);
        return post(url, body, headerMap, clz);
    }

    /**
     * http POST 方法（不带返回体）
     *
     * @param user    用户
     * @param url     url
     * @param body    请求体
     * @param cloudId 云id
     */
    public static void post(User user, String url, String body, String cloudId) {
        Map<String, String> headerMap = makeHeader(user, cloudId);
        post(url, body, headerMap);
    }

    /**
     * http PUT 方法（带返回体）
     *
     * @param user    用户
     * @param url     url
     * @param body    请求体
     * @param cloudId 云id
     * @param clz     返回体类型
     * @param <T>     T
     * @return 返回数据
     */
    public static <T> ResponseEntity<T> put(User user, String url, String body, String cloudId, Class<T> clz) {
        Map<String, String> headerMap = makeHeader(user, cloudId);
        return put(url, body, headerMap, clz);
    }

    /**
     * http PUT 方法（不带返回体）
     *
     * @param user    用户
     * @param url     url
     * @param body    请求体
     * @param cloudId 云id
     */
    public static void put(User user, String url, String body, String cloudId) {
        Map<String, String> headerMap = makeHeader(user, cloudId);
        put(url, body, headerMap);
    }

    /**
     * 组装http header
     *
     * @param user    用户
     * @param cloudId 云id
     * @return http header
     */
    private static Map<String, String> makeHeader(User user, String cloudId) {
        Map<String, String> headerMap = new HashMap<>(16);
        headerMap.put(HEADER_CLOUD_ID, cloudId);
        headerMap.put(X_AUTH_TOKEN, user.getToken());
        headerMap.put(CLIENT_ADDRESS, user.getLocalIp());
        return headerMap;
    }
}
