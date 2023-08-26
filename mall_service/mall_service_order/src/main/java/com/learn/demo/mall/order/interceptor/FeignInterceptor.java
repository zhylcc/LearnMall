package com.learn.demo.mall.order.interceptor;

import com.learn.demo.mall.order.entity.NonWebRequestAttributes;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
        String authorization = null;
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            //传递令牌
            authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        } else if (requestAttributes instanceof NonWebRequestAttributes) {
            authorization = (String) requestAttributes.getAttribute(HttpHeaders.AUTHORIZATION, 0);
        }
        if (StringUtils.isNotBlank(authorization)) {
            requestTemplate.header(AUTHORIZATION_HEADER, authorization);
        }
    }
}
