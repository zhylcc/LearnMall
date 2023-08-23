package com.learn.demo.mall.order.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @author zh_cr
 */
@Component
public class FeignInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "authorization";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        //传递令牌
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) {
            return;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            if (AUTHORIZATION_HEADER.equalsIgnoreCase(headerName)){
                String headerValue = request.getHeader(headerName); // Bearer jwt
                //传递令牌
                requestTemplate.header(headerName,headerValue);
            }
        }
    }
}
