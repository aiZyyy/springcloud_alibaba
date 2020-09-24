package com.zy.kits.snowflakeservice.utils;

import java.security.MessageDigest;

/**
 * Created with IDEA
 *
 * @Description: TODO
 * @Author:ZY
 * @Date:2019/4/15
 * @Time:10:44
 */
public class MD5Util {
    /**
     * @param s 需要加密的字符串
     * @return String    返回类型
     * @Title: getMD5Code
     * @Description: 获取加密的字符串(这里用一句话描述这个方法的作用)
     */
    public final static String getMD5Code(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}