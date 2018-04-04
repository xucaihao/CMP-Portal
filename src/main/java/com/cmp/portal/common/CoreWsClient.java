package com.cmp.portal.common;

import com.cmp.portal.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static com.cmp.portal.common.Constance.*;

public class CoreWsClient extends BaseClient {

    static public <T> ResponseEntity<T> get(User user, String url, String cloudId, Class<T> clz) {
        Map<String, String> headerMap = new HashMap<>(16);
        if (StringUtils.isEmpty(cloudId)) {
            headerMap.put(HEADER_CLOUD_ID, cloudId);
        }
        headerMap.put(X_AUTH_TOKEN, user.getToken());
        return get(url, headerMap, clz);
    }

    static void delete(User user, String url, String cloudId) {
        Map<String, String> headerMap = makeHeader(user, cloudId);
        delete(url, headerMap);
    }

    static <T> ResponseEntity<T> post(User user, String url, String body, String cloudId, Class<T> clz) {
        Map<String, String> headerMap = makeHeader(user, cloudId);
        return post(url, body, headerMap, clz);
    }

    static void post(User user, String url, String body, String cloudId) {
        Map<String, String> headerMap = makeHeader(user, cloudId);
        post(url, body, headerMap);
    }

    static <T> ResponseEntity<T> put(User user, String url, String body, String cloudId, Class<T> clz) {
        Map<String, String> headerMap = makeHeader(user, cloudId);
        return put(url, body, headerMap, clz);
    }

    static void put(User user, String url, String body, String cloudId) {
        Map<String, String> headerMap = makeHeader(user, cloudId);
        put(url, body, headerMap);
    }

    private static Map<String, String> makeHeader(User user, String cloudId) {
        Map<String, String> headerMap = new HashMap<>(16);
        headerMap.put(HEADER_CLOUD_ID, cloudId);
        headerMap.put(X_AUTH_TOKEN, user.getToken());
        headerMap.put(CLIENT_ADDRESS, user.getLocalIp());
        return headerMap;
    }
}
