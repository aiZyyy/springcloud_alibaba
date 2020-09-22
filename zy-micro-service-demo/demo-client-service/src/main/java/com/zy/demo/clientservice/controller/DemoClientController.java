package com.zy.demo.clientservice.controller;

import com.zy.demo.nacos.config.share.DemoServiceApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ZY
 * @date 2020/9/22 11:09
 * @Description:
 * @Version 1.0
 */
@RestController
public class DemoClientController {

    @Resource
    private DemoServiceApi demoServiceApi;

    @RequestMapping("/getValue")
    public String getValue() {
        return demoServiceApi.getValue();
    }
}
