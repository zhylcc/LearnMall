package com.learn.demo.mall.common.utils;

import java.util.Random;

/**
 * @author zh_cr
 */
public class RandomUtil {
    public static String getRandomString() {
        int length = 15;
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer builder = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            builder.append(base.charAt(number));
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        String randomString = RandomUtil.getRandomString();
        System.out.println(randomString);
    }
}
