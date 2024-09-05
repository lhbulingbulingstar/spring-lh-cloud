package com.lh.cloud.framework.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: lh
 * @Date: 2022/1/18 10:05
 */
@Configuration
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor{
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes!=null){
            HttpServletRequest request = attributes.getRequest();
            //添加token
            requestTemplate.header("Authorization", request.getHeader("Authorization"));
        }
    }
}
