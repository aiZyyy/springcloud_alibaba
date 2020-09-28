package com.zy.gateway.routeservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created with IntelliJ IDEA
 *
 * @author MiaoWoo
 */
@SpringCloudApplication
@MapperScan("com.zy.**.mapper")
@ComponentScan("com.zy")
public class GatewayRouteServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayRouteServiceApplication.class, args);
    }
}
