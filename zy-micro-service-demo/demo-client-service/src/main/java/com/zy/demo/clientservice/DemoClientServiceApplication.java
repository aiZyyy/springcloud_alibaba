package com.zy.demo.clientservice;

import com.zy.apps.common.annotation.ZyMicroServiceApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;

@ZyMicroServiceApplication
public class DemoClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoClientServiceApplication.class, args);
    }
}
