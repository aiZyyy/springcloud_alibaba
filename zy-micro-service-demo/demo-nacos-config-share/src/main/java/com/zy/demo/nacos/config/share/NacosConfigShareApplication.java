package com.zy.demo.nacos.config.share;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@RefreshScope
public class NacosConfigShareApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigShareApplication.class, args);
    }

    @Value("${nacos.share}")
    private String share;

    @Value("${nacos}")
    private String shareConfig1;

    @RequestMapping("/getValue")
    public String getValue() {
        return share;
    }

    @RequestMapping("/getShare1")
    public String getShare1() {
        return shareConfig1;
    }
}
