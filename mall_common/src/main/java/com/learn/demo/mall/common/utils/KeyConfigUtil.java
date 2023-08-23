package com.learn.demo.mall.common.utils;

/**
 * 动态配置（主要用于管理测试开关）
 * @author zh_cr
 */
public class KeyConfigUtil {

    /**
     * 测试时的用户名
     */
    public static String getTestUsername() {
        return "test";
    }

    /**
     * 购物车redis缓存key前缀
     */
    public static String getCartKeyPrefix() {
        return "cart_";
    }

    /**
     * 系统管理微服务签发jwt是的密钥
     */
    public static String getSystemJwtKey() {
        return "mallSystem";
    }

    /**
     * 索引导入队列
     */
    public static String getIndexImportQueue() {
        return "indexBatchQueue";
    }

    /**
     * 索引删除队列
     */
    public static String getIndexDeleteQueue() {
        return "indexDeleteQueue";
    }
}
