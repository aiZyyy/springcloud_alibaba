//package com.zy.apps.common.config;
//
//import com.zy.apps.common.interceptor.okhttp3.RetryInterceptor;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import okhttp3.OkHttpClient;
//
///**
// * Created with IntelliJ IDEA
// *
// * @author ZY
// * Created on 2018/12/20 9:52.
// */
//@Configuration
//public class OkHttpConfigurer {
//    @Bean
//    @ConditionalOnMissingBean
//    public OkHttpClient okHttpClient() {
//        return new OkHttpClient.Builder()
//                .addInterceptor(new RetryInterceptor(3, 500))
//                .followRedirects(false).followSslRedirects(false).build();
//    }
//}
