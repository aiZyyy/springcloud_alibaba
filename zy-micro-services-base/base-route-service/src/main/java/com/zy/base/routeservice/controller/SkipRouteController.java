package com.zy.base.routeservice.controller;

import com.zy.base.routeservice.domain.form.RouteSkipAddForm;
import com.zy.base.routeservice.domain.form.RouteSkipDelForm;
import com.zy.base.routeservice.service.BaseRouteService;
import com.zy.base.routeservice.service.SkipRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author ZY
 * @date 2020/10/13 11:14
 * @Description:
 * @Version 1.0
 */
@RestController
@Slf4j
public class SkipRouteController {

    @Autowired
    private SkipRouteService skipRouteService;

    /**
     * 可跳过认证路径添加
     *
     * @param routeSkipForm
     */
    @PostMapping("/route/skip/add")
    public String skipRouteAdd(@Valid @RequestBody RouteSkipAddForm routeSkipForm) {
        skipRouteService.skipRouteAdd(routeSkipForm);
        return "success";
    }

    /**
     * 可跳过认证路径删除
     *
     * @param routeSkipDelForm
     */
    @PostMapping("/route/skip/del")
    public String skipRouteDel(@Valid @RequestBody RouteSkipDelForm routeSkipDelForm) {
        skipRouteService.skipRouteDel(routeSkipDelForm);
        return "del success";
    }

    /**
     * 可跳过认证路径刷新
     * @return
     */
    @PostMapping("/route/skip/refresh")
    public String skipRouteRefresh() {
        skipRouteService.skipRouteRefresh();
        return "refresh success";
    }
}
