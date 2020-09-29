package com.zy.gateway.checksign.common;

import com.alibaba.fastjson.JSONObject;
import com.zy.gateway.checksign.common.oauth.AuthMessage;
import com.zy.gateway.checksign.common.oauth.SignerBuilder;
import com.zy.gateway.checksign.common.oauth.domain.AuthConsumer;
import com.zy.gateway.checksign.common.oauth.method.ISignatureMethod;

/**
 * @Author: ZY
 * @Date: 2019/8/19 11:11
 * @Version 1.0
 * @Description:
 */
public class test {
    public static void main(String[] args) {
        AuthConsumer authConsumer = AuthConsumer.builder().key("app106196537394204672")
                .secret("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIiTBrybn79cwSCqI+Y9QmbIG1gdyvi/5q7e2mKAfd5uI9f7QvURtPYbfyVHqJ1LelX4NprUPB+yx4yXn9EAM/7kFe40C64hAd9yfUpDI8mj430Md6Gmvr8yReJqYThgyVlJ+hyiOlGIN7OK77Ncq5sgtjwYU9LRQEJP4C5cISoFAgMBAAECgYBiCll9r6Hft3AUSM74iuVN7zxh5xE7vFaXFfdjWqYAtMQaWY7ZalqjiV2en3BVakdpy4M3Zj+66ZYCQR5C5Iht1ne0mEt03b3TW9afcACXoS6CG98T12IZB/1T80NC2FvFUo+xL402P+Pky+y0RFn8N5QTpw2Zs+f+UW0fjVhvAQJBAMpNTWJQ2VKCmu0164XASuwt8k6enCXmrkca0FdxlfzVYKDVhGqRftoObWxo6f42sAEx6oAsOgDmQ9mPNLaKHaECQQCs03CpgY8NGmMwzijYDT/epcxUxvJK19MMaeuIv5ty/WtjGwTa9MmCx+wKu61ApZsSBTiDi+EcsntvNnhHSgnlAkAL79HAGlsoZjJ0Crlx8+23z+I7R1qZVeo0od1nO3mJKKIB+3dLvngIr88/FVWxPVLXTzWyCGoHwY6BWNTYbH/hAkAqmsU/RC0bsdGaCniPKlUAcib5D0JTqPjiKoph37pWkpqSh+qef5Hdp6NM0g9XzHt56ceQxj7scCN1uxvTlzDxAkEAl5737LHypAcsGJzzFYdZYhXXF6AzqDHBpE3YdWnL/w1qFVa0oJIJAR1IW1reyV0nlXqHKbbvkGGJwHOPZfXQ3w==")
                .build();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sequence", "0123456789");
        jsonObject.put("app_id", "app235622944347394048");
        jsonObject.put("biz_content", "");
        long timeMillis = System.currentTimeMillis();
        System.out.println(timeMillis);
        jsonObject.put("timestamp", String.valueOf(timeMillis));
        jsonObject.put("sign_type", "RSA");
        jsonObject.put("charset", "utf-8");
        jsonObject.put("method", "zy-nacos-config.getValue");
        jsonObject.put("version", "1.0");
        AuthMessage authMessage = new AuthMessage(jsonObject);
        ISignatureMethod rsa = SignerBuilder.newSigner(authMessage);
        String signature = rsa.getSignature(authMessage, authConsumer);
        System.out.println(signature);
    }
}
