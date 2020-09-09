package com.zy.gateway.core.route.service.service;

import com.zy.gateway.core.route.service.domain.entity.GatewayRoute;
import com.zy.gateway.core.route.service.domain.form.*;

import java.util.List;

/**
 * @Author: ZY
 * @Date: 2019/10/15 17:23
 * @Version 1.0
 * @Description:
 */

public interface GatewayRouteService {

    void skipRouteAdd(RouteSkipAddForm routeSkipForm);

    void skipRouteDel(RouteSkipDelForm routeSkipDelForm);

    /**
     * 查询路由信息
     *
     * @param routeId
     * @return
     */
    GatewayRoute findOneByRouteId(String routeId);

    /**
     * 查询所有路由信息
     *
     * @return
     */
    List<GatewayRoute> findListAll();

    /**
     * 新增路由
     */
    GatewayRoute addRoute(RouteForm routeForm);

    /**
     * 修改路由
     */
    GatewayRoute updateRoute(RouteUpdateForm routeUpdateForm);

    /**
     * 开启路由
     * @param routeForm
     * @return
     */
    Integer openRoute(RouteIdForm routeForm);

    /**
     * 停用路由
     */
    Integer delRoute(RouteIdForm routeForm);
}
