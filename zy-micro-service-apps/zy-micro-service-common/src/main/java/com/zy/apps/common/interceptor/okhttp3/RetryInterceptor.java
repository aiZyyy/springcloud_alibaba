package com.zy.apps.common.interceptor.okhttp3;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created with IntelliJ IDEA
 *
 * @author ZY
 * Created on 2019/1/5 10:41.
 */
@Slf4j
public class RetryInterceptor implements Interceptor {
    private Integer retryTimes;
    private Integer retryDelay;

    public RetryInterceptor(Integer retryTimes, Integer retryDelay) {
        this.retryTimes = retryTimes;
        this.retryDelay = retryDelay;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return process(chain.request(), chain, 1);
    }

    private Response process(Request request, Chain chain, Integer times) throws IOException {
        try {
            return chain.proceed(request.newBuilder().build());
        } catch (SocketException | SocketTimeoutException e) {
            log.warn("request timeout retry url: {} method: {} times: {}", request.url(), request.method(), times);
            if (times >= retryTimes) {throw e;}
            try {
                Thread.sleep(retryDelay * retryTimes);
            } catch (InterruptedException ignored) {
            }
            return process(request, chain, times + 1);
        }
    }

}
