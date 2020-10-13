package com.zy.base.routeservice.service;

import com.zy.base.routeservice.domain.entity.BaseRoute;
import com.zy.base.routeservice.domain.form.*;

import java.util.List;

/**
 * @Author: ZY
 * @Date: 2019/10/15 17:23
 * @Version 1.0
 * @Description:
 */

public interface BaseRouteService {

    /**
     * 查询路由信息
     *
     * @param routeId
     * @return
     */
    BaseRoute findOneByRouteId(String routeId);

    /**
     * 查询所有路由信息
     *
     * @return
     */
    List<BaseRoute> findListAll();

    /**
     * 新增路由
     */
    BaseRoute addRoute(RouteForm routeForm);

    /**
     * 修改路由
     */
    BaseRoute updateRoute(RouteUpdateForm routeUpdateForm);

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
