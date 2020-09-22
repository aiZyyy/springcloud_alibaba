package com.zy.demo.nacos.config.share;

import com.zy.apps.common.annotation.ZyMicroServiceApplication;
import org.springframework.boot.SpringApplication;

@ZyMicroServiceApplication
public class NacosConfigShareApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigShareApplication.class, args);
    }
}
