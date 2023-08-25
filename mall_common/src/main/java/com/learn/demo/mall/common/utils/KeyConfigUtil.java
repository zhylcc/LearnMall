package com.learn.demo.mall.common.utils;

/**
 * 动态配置（主要用于管理测试开关）
 * @author zh_cr
 */
public class KeyConfigUtil {

    /**
     * 微服务网关鉴权开关
     */
    public static boolean isNeedAuthorized() {
        return true;
    }

    /**
     * 跳转登录开关
     */
    public static boolean isRedirect() {
        return false;
    }

    /**
     * 测试时的用户名
     */
    public static String getTestUsername() {
        return "";
    }

    /**
     * 设置token的header名称
     */
    public static String getTokenHeader() {
        return "token";
    }

    /**
     * 设置jti的cookie名称
     */
    public static String getJtiCookieName() {
        return "jti";
    }

    /**
     * 购物车redis缓存key前缀
     */
    public static String getCartKeyPrefix() {
        return "cart_";
    }

    /**
     * 系统管理微服务签发jwt的密钥
     */
    public static String getSystemJwtKey() {
        return "mallSystem";
    }

    /**
     * 索引导入队列
     */
    public static String getSpuMarketableExchange() {
        return "spuMarketableExchange";
    }


    /**
     * web前台域名
     */
    public static String getWebDomain() {
        return "http://localhost";
    }

    /**
     * 授权服务器id
     */
    public static String getAuthInstanceId() {
        return "auth";
    }
}
