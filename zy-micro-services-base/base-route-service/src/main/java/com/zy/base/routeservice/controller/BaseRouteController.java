package com.zy.base.routeservice.controller;

import com.zy.apps.common.domain.vo.Result;
import com.zy.base.routeservice.domain.entity.BaseRoute;
import com.zy.base.routeservice.domain.form.RouteForm;
import com.zy.base.routeservice.domain.form.RouteIdForm;
import com.zy.base.routeservice.domain.form.RouteUpdateForm;
import com.zy.base.routeservice.service.BaseRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: ZY
 * @Date: 2019/8/12 18:15
 * @Version 1.0
 * @Description:
 */
@RestController
@Slf4j
public class BaseRouteController {

    @Autowired
    private BaseRouteService baseRouteService;

    /**
     * 路由信息添加
     *
     * @param routeForm
     * @return
     */
    @PostMapping("/route/base/addRoute")
    public Result<?> addRoute(@Valid @RequestBody RouteForm routeForm) {
        BaseRoute baseRoute = baseRouteService.addRoute(routeForm);
        return Result.ok(baseRoute);
    }

    /**
     * 路由信息修改
     *
     * @param routeForm
     * @return
     */
    @PostMapping("/route/base/updateRoute")
    public Result<?> updateRoute(@Valid @RequestBody RouteUpdateForm routeForm) {
        return Result.ok(baseRouteService.updateRoute(routeForm));
    }

    /**
     * 路由信息查询
     *
     * @param
     * @return
     */
    @PostMapping("/route/base/query")
    public Result<?> queryRoute() {
        return Result.ok(baseRouteService.findListAll());
    }

    /**
     * 路由状态开启
     *
     * @param routeForm
     * @return
     */
    @PostMapping("/route/base/open")
    public Result<?> openRoute(@Valid @RequestBody RouteIdForm routeForm) {
        return Result.ok(baseRouteService.openRoute(routeForm));
    }

    /**
     * 路由信息删除
     *
     * @param routeDelForm
     * @return
     */
    @PostMapping("/route/base/delRoute")
    public Result<?> delRoute(@Valid @RequestBody RouteIdForm routeDelForm) {
        return Result.ok(baseRouteService.delRoute(routeDelForm));
    }

    /**
     * 可跳过认证路径刷新
     *
     * @return
     */
    @PostMapping("/route/base/refresh")
    public Result<?> baseRouteRefresh() {
        baseRouteService.baseRouteRefresh();
        return Result.ok();
    }
}
