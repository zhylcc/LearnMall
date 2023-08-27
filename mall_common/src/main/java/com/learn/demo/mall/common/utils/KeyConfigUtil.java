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
     * 秒杀商品信息缓存key前缀
     */
    public static String getSeckillGoodsKeyPrefix() {
        return "seckill_goods_";
    }

    /**
     * 秒杀商品库存缓存key前缀
     */
    public static String getSeckillGoodsStockKeyPrefix() {
        return "seckill_goods_stock_";
    }

    /**
     * 秒杀下单消息confirm机制缓存key前缀
     */
    public static String getSeckillConfirmKeyPrefix() {
        return "seckill_confirm_";
    }

    /**
     * 秒杀下单用户同商品防重缓存key前缀
     */
    public static String getSeckillUserGoodsKeyPrefix() {
        return "seckill_user_goods_";
    }

    /**
     * 秒杀下单用户同商品防重缓存key有效时间，s
     */
    public static Long getSeckillUserGoodsKeyExpire() {
        return 5L;
    }

    /**
     * 秒杀下单接口隐藏随机码缓存key前缀，s
     */
    public static String getSeckillRandomKeyPrefix() {
        return "seckill_random_";
    }

    /**
     * 秒杀下单接口隐藏随机码缓存key有效时间，s
     */
    public static Long getSeckillRandomKeyExpire() {
        return 60L;
    }

    /**
     * 系统管理微服务签发jwt的密钥
     */
    public static String getSystemJwtKey() {
        return "mallSystem";
    }

    /**
     * 商品上下架与索引同步
     */
    public static final String SPU_MARKETABLE_EXCHANGE = "spuMarketableExchange";  // 商品上、下架
    public static final String INDEX_IMPORT_QUEUE = "indexImportQueue";  // 批量导入相关sku索引
    public static final String INDEX_DELETE_QUEUE = "indexDeleteQueue";  // 批量删除相关sku索引
    public static final String INDEX_IMPORT_KEY = "up";
    public static final String INDEX_DELETE_KEY = "down";

    /**
     * 增加用户积分
     */
    public static final String USER_ADD_POINTS_EXCHANGE = "userAddPointsExchange";  // 用户增加积分
    public static final String TO_ADD_POINTS_QUEUE = "toAddPointsQueue";  // 增加积分
    public static final String FINISH_ADD_POINTS_QUEUE = "finishAddPointsQueue";  // 增加积分完成
    public static final String TO_ADD_POINTS_KEY = "to";
    public static final String FINISH_ADD_POINTS_KEY = "finish";

    /**
     * 订单超时处理
     */
    public static final String ORDER_TIMEOUT_QUEUE = "orderTimeoutQueue";
    public static final String ORDER_CREATE_QUEUE = "orderCreateQueue";

    /**
     * 订单支付
     */
    public static final String ORDER_PAY_QUEUE = "orderPayQueue";

    /**
     * 秒杀异步下单
     */
    public static final String SECKILL_ORDER_QUEUE = "seckillOrderQueue";



    /**
     * 积分任务缓存过期时间，s
     */
    public static Long getTaskRedisExpire() {
        return 30L;
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

    /**
     * 默认订单支付状态
     * SUCCESS
     * NOT_PAY
     */
    public static String getTestTradeStatus() {
        return "SUCCESS";
    }

    public static String getTestOrderId() {
        return "887976366080";
    }
}
