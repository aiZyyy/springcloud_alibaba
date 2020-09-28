package com.zy.gateway.core.routeservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * Created with IntelliJ IDEA
 *
 * @author MiaoWoo
 */
@SpringCloudApplication
@MapperScan("com.zy.**.mapper")
public class CoreRouteServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoreRouteServiceApplication.class, args);
    }
}
