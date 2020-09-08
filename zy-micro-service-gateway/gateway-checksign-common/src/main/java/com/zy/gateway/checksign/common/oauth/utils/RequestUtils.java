package com.zy.gateway.checksign.common.oauth.utils;


import com.zy.gateway.checksign.common.oauth.AuthMessage;
import com.zy.gateway.checksign.common.oauth.SignerBuilder;
import com.zy.gateway.checksign.common.oauth.domain.AuthConsumer;
import com.zy.gateway.checksign.common.oauth.method.ISignatureMethod;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @Author: ZY
 * @Date: 2019/8/12 15:02
 * @Version 1.0
 * @Description:
 */
public class RequestUtils {
    /**
     * 生成签名,并携带发送请求
     *
     * @param url        网关路径
     * @param appId      应用名称
     * @param signType   加密类型
     * @param charset    编码类型
     * @param method     请求地址
     * @param timestamp  时间戳
     * @param bizContent 请求参数
     */
    public static String checkSignature(String url, String sequence,String appId, String signType, String charset, String method, String timestamp, String bizContent, String privateKey) throws IOException {

        String body = "";
        // 生成签名
        AuthMessage authMessage = new AuthMessage();
        authMessage.addParameter("sequence",sequence);
        authMessage.addParameter("app_id", appId);
        authMessage.addParameter("biz_content", bizContent);
        authMessage.addParameter("timestamp", timestamp);
        authMessage.addParameter("sign_type", signType);
        authMessage.addParameter("charset", charset);
        authMessage.addParameter("method", method);
        ISignatureMethod rsa = SignerBuilder.newSigner(authMessage);
        AuthConsumer authConsumer = AuthConsumer.builder().key(appId).secret(privateKey).build();
        String signature = rsa.getSignature(authMessage, authConsumer);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("sequence", sequence);
        jsonObject.append("app_id", appId);
        jsonObject.append("sign_type", signType);
        jsonObject.append("charset", charset);
        jsonObject.append("method", method);
        jsonObject.append("timestamp", timestamp);
        jsonObject.append("biz_content", bizContent);
        jsonObject.append("sign", signature);
        //装填参数
        StringEntity s = new StringEntity(jsonObject.toString(), "utf-8");
        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        //设置参数到请求对象中
        httpPost.setEntity(s);
        System.out.println("请求地址：" + url);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = httpClient.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }
}
