package com.zy.kits.snowflakeservice.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 *
 * @Description:
 * @Author: ZY
 * @Date: 2018-12-20
 * @Time: 11:23
 */
public class OrderId extends Thread {
    private static long orderNum = 0L;
    private static String date;

    /**
     * 生成订单编号
     *
     * @return
     */
    public static synchronized String getOrderNo() {
        String str = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        if (date == null || !date.equals(str)) {
            date = str;
            orderNum = 0L;
        }
        orderNum++;
        long orderNo = Long.parseLong((date)) * 1000;
        orderNo += orderNum;
        return orderNo + "";
    }
}
