package com.zy.demo.nacos.config.share.controller;

import com.zy.demo.nacos.config.share.DemoServiceApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZY
 * @date 2020/9/22 11:01
 * @Description:
 * @Version 1.0
 */
@RestController
public class DemoController implements DemoServiceApi {

    @Value("${nacos.share}")
    private String share;

    @RequestMapping("/getValue")
    public String getValue() {
        return share;
    }

}
