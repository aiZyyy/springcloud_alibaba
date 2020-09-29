package com.zy.base.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author ZY
 * @date 2020/9/29 16:41
 * @Description:
 * @Version 1.0
 */
@SpringCloudApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
