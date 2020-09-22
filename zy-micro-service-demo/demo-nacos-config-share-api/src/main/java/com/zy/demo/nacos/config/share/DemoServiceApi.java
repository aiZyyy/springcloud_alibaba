package com.zy.demo.nacos.config.share;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ZY
 * @date 2020/9/22 11:02
 * @Description:
 * @Version 1.0
 */
@FeignClient("demo-nacos-config-share")
public interface DemoServiceApi {

    @RequestMapping("/getValue")
    String getValue();
}
