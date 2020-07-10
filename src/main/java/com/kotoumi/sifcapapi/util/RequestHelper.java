package com.kotoumi.sifcapapi.util;

import com.alibaba.fastjson.JSON;
import com.kotoumi.sifcapapi.model.vo.request.Request;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Component
public class RequestHelper {

    private static final RestTemplate restTemplate = new RestTemplate();

    /**
     * 发送HTTP POST请求，默认给一个json的请求头
     * @param url 请求地址
     * @param request 请求体
     * @return 请求结果
     */
    public static String httpPost(String url, Request request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
        return httpPost(url, request, headers);
    }

    /**
     * 发送HTTP POST请求
     * @param url 请求地址
     * @param request 请求体
     * @param httpHeaders 请求头
     * @return 请求结果
     */
    public static String httpPost(String url, Request request, HttpHeaders httpHeaders) {

        // 参数校验
        if (StringUtils.isBlank(url)) {
            log.error("httpPost URL is blank!");
            return null;
        }
        if (request == null) {
            log.error("httpPost request is null!");
            return null;
        }

        log.info("httpPost URL: {}", url);
        log.info("httpPost request: {}", JSON.toJSONString(request));
        log.info("httpPost headers: {}", JSON.toJSONString(httpHeaders));

        HttpEntity<String> requestEntity = new HttpEntity<>(JSON.toJSONString(request), httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        log.info("httpPost response: {}", JSON.toJSONString(response));

        // 返回参数校验
        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("httpPost error: status code {}", response.getStatusCode());
            return null;
        } else {
            return response.getBody();
        }
    }

    /**
     * 发送HTTP GET请求，默认没有header
     * @param url 请求地址
     * @return 请求结果
     */
    public static String httpGet(String url) {
        return httpGet(url, Collections.emptyMap(), null);
    }

    /**
     * 发送带参数的HTTP GET请求，默认没有header
     * @param url 请求地址
     * @param params 参数
     * @return 请求结果
     */
    public static String httpGet(String url, Map<String, String> params) {
        return httpGet(url, params, null);
    }

    /**
     * 发送HTTP GET请求
     * @param url 请求地址
     * @param params 参数
     * @param httpHeaders 请求头
     * @return 请求结果
     */
    public static String httpGet(String url, Map<String, String> params, HttpHeaders httpHeaders) {
        // 参数校验
        if (StringUtils.isBlank(url)) {
            log.error("httpGet URL is blank!");
        }

        log.info("httpGet URL: {}", url);
        log.info("httpGet params: {}", JSON.toJSONString(params));

        // 发送请求
        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                String.class, params);
        log.info("httpGet response: {}", JSON.toJSONString(response));
        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("httpGet error: status code {}", response.getStatusCode());
            return null;
        } else {
            return response.getBody();
        }
    }

}
