package com.learn.demo.mall.user.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 * @author zh_cr
 */
public class CookieUtil {

    /**
     * 设置cookie
     */
    public static void addCookie(HttpServletResponse response, String domain, String path, String name,
                                 String value, int maxAge, boolean httpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(httpOnly);
        response.addCookie(cookie);
    }

    /**
     * 根据cookie名称读取cookie
     */
    public static String readCookie(HttpServletRequest request, String name) {
        String cookieValue = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if (cookieName.equals(name)) {
                    cookieValue = cookie.getValue();
                    break;
                }
            }
        }
        return cookieValue;
    }

    /**
     * 删除cookie
     */
    public static String deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        String cookieValue = readCookie(request, cookieName);
        if (StringUtils.isEmpty(cookieValue)) {
            return null;
        }
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return cookieValue;
    }
}
