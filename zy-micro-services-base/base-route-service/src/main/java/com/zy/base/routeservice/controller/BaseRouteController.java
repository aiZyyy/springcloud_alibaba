package com.zy.base.routeservice.controller;

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
import java.util.List;

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
    public BaseRoute addRoute(@Valid @RequestBody RouteForm routeForm) {
        return baseRouteService.addRoute(routeForm);
    }

    /**
     * 路由信息修改
     *
     * @param routeForm
     * @return
     */
    @PostMapping("/route/base/updateRoute")
    public BaseRoute updateRoute(@Valid @RequestBody RouteUpdateForm routeForm) {
        return baseRouteService.updateRoute(routeForm);
    }

    /**
     * 路由信息查询
     *
     * @param
     * @return
     */
    @PostMapping("/route/base/query")
    public List<BaseRoute> queryRoute() {
        return baseRouteService.findListAll();
    }

    /**
     * 路由状态开启
     *
     * @param routeForm
     * @return
     */
    @PostMapping("/route/base/open")
    public Integer openRoute(@Valid @RequestBody RouteIdForm routeForm) {
        return baseRouteService.openRoute(routeForm);
    }

    /**
     * 路由信息删除
     *
     * @param routeDelForm
     * @return
     */
    @PostMapping("/route/base/delRoute")
    public Integer delRoute(@Valid @RequestBody RouteIdForm routeDelForm) {
        return baseRouteService.delRoute(routeDelForm);
    }
}
