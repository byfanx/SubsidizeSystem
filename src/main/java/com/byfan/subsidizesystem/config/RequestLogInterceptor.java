package com.byfan.subsidizesystem.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: RequestLogInterceptor
 * @Description: 请求日志拦截器
 * @Author: byfan
 * @Date: 2022/4/10 22:52
 */
@Slf4j
public class RequestLogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        try {
            Map<String, Object> requestDetails = new HashMap<>();
//            requestDetails.put("request_url", request.getRequestURL());
            requestDetails.put("request_url", request.getRequestURI());
            requestDetails.put("request_method", request.getMethod());
//            requestDetails.put("request_parameter", request.getParameterMap());
            requestDetails.put("request_parameter", request.getQueryString());
            log.info("request details ：{}", JSON.toJSON(requestDetails));
        } catch (Exception e) {
            log.error("RequestLogInterceptor Log Record error ", e);
        }
        return true;
    }
}
