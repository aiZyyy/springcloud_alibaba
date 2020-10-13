package com.zy.base.routeservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringCloudApplication
@MapperScan("com.zy.**.mapper")
@ComponentScan("com.zy")
public class BaseRouteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseRouteServiceApplication.class, args);
    }

}
