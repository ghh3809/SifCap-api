package com.kotoumi.sifcapapi.filter;

import com.alibaba.fastjson.JSON;
import com.kotoumi.sifcapapi.constant.LogConstant;
import com.kotoumi.sifcapapi.util.LoggerHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoggerFilter implements Filter {

    private static final String TIME_ACCEPT_REQUEST = "time_accept_request";
    private static final String TIME_RETURN_REQUEST = "time_return_request";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    /**
     * 主要用于记录日志
     * @param servletRequest servletRequest
     * @param servletResponse servletResponse
     * @param filterChain filterChain
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        // 记录request日志
        long requestTime = System.currentTimeMillis();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String unitLogId = LoggerHelper.generateLogId();
        MDC.put(LogConstant.LOG_UNIT_LOG_ID_KEY, unitLogId);
        LoggerHelper.logMonitor(
                unitLogId,
                request.getServletPath(),
                TIME_ACCEPT_REQUEST,
                0,
                "0",
                ""
        );
        log.info("Path: {}", request.getServletPath());
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            headers.put(key, request.getHeader(key));
        }
        log.info("Header: {}", JSON.toJSONString(headers));
        log.info("Params: {}", JSON.toJSONString(request.getParameterMap()));

        // 业务逻辑
        filterChain.doFilter(servletRequest, servletResponse);

        // 记录response日志
        if (StringUtils.isBlank(MDC.get(LogConstant.LOG_RETURN_CODE_KEY))) {
            MDC.put(LogConstant.LOG_RETURN_CODE_KEY, "");
        }
        if (StringUtils.isBlank(MDC.get(LogConstant.LOG_RETURN_MESSAGE_KEY))) {
            MDC.put(LogConstant.LOG_RETURN_MESSAGE_KEY, "");
        }
        long responseTime = System.currentTimeMillis();
        LoggerHelper.logMonitor(
                unitLogId,
                request.getServletPath(),
                TIME_RETURN_REQUEST,
                responseTime - requestTime,
                MDC.get(LogConstant.LOG_RETURN_CODE_KEY),
                MDC.get(LogConstant.LOG_RETURN_MESSAGE_KEY)
        );
        MDC.remove(LogConstant.LOG_UNIT_LOG_ID_KEY);
        MDC.remove(LogConstant.LOG_RETURN_CODE_KEY);
        MDC.remove(LogConstant.LOG_RETURN_MESSAGE_KEY);
    }

    @Override
    public void destroy() {

    }

}
