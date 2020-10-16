package com.zy.base.routeservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zy.apps.common.constant.GatewayConstant;
import com.zy.apps.common.kits.DozerBeanKit;
import com.zy.apps.common.utils.MapperUtil;
import com.zy.base.routeservice.domain.entity.*;
import com.zy.base.routeservice.domain.form.RouteForm;
import com.zy.base.routeservice.domain.form.RouteIdForm;
import com.zy.base.routeservice.domain.form.RouteUpdateForm;
import com.zy.base.routeservice.mapper.BaseRouteMapper;
import com.zy.base.routeservice.service.BaseRouteService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

/**
 * @Author: ZY
 * @Date: 2019/10/15 17:23
 * @Version 1.0
 * @Description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BaseRouteServiceImpl implements BaseRouteService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private BaseRouteMapper gatewayRouteMapper;


    @Override
    public BaseRoute findOneByRouteId(String routeId) {
        BaseRouteExample gatewayRouteExample = new BaseRouteExample();
        gatewayRouteExample.createCriteria().andRouteIdEqualTo(routeId);
        List<BaseRoute> gatewayRoutes = gatewayRouteMapper.selectByExample(gatewayRouteExample);
        if (CollectionUtils.isNotEmpty(gatewayRoutes)) {
            return gatewayRoutes.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<BaseRoute> findListAll() {
        BaseRouteExample gatewayRouteExample = new BaseRouteExample();
        List<BaseRoute> gatewayRoutes = gatewayRouteMapper.selectByExample(gatewayRouteExample);
        return gatewayRoutes;
    }

    @Override
    public BaseRoute addRoute(RouteForm routeForm) {
        String routeId = routeForm.getRouteId();
        BaseRoute oneByRouteId = findOneByRouteId(routeId);
        if (Objects.isNull(oneByRouteId)) {
            BaseRoute gatewayRoute = DozerBeanKit.map(routeForm, BaseRoute.class);
            //插入数据库
            gatewayRouteMapper.insertSelective(gatewayRoute);
            //存入redis
            RouteDefinition routeDefinition = assembleRouteDefinition(gatewayRoute);
            redisTemplate.opsForHash().put(GatewayConstant.GATEWAY_ROUTES, routeId, JSON.toJSONString(routeDefinition));
            return gatewayRoute;
        } else {
            return null;
        }
    }

    @Override
    public BaseRoute updateRoute(RouteUpdateForm routeForm) {
        String routeId = routeForm.getRouteId();
        BaseRouteExample gatewayRouteExample = new BaseRouteExample();
        gatewayRouteExample.createCriteria().andRouteIdEqualTo(routeId);
        List<BaseRoute> gatewayRoutes = gatewayRouteMapper.selectByExample(gatewayRouteExample);
        if (CollectionUtils.isNotEmpty(gatewayRoutes)) {
            BaseRoute gatewayRoute = DozerBeanKit.map(routeForm, BaseRoute.class);
            //修改数据库信息
            gatewayRouteMapper.updateByExampleSelective(gatewayRoute, gatewayRouteExample);
            BaseRoute oneByRouteId = findOneByRouteId(routeId);
            RouteDefinition routeDefinition = assembleRouteDefinition(oneByRouteId);
            //如果为启用状态路由,更新redis信息
            if (oneByRouteId.getEnable() == 0) {
                redisTemplate.opsForHash().put(GatewayConstant.GATEWAY_ROUTES, routeId, JSON.toJSONString(routeDefinition));
            }
            return gatewayRoute;
        } else {
            return null;
        }
    }

    @Override
    public Integer openRoute(RouteIdForm routeForm) {
        String routeId = routeForm.getRouteId();
        BaseRouteExample gatewayRouteExample = new BaseRouteExample();
        gatewayRouteExample.createCriteria().andRouteIdEqualTo(routeId).andEnableEqualTo(0);
        List<BaseRoute> gatewayRoutes = gatewayRouteMapper.selectByExample(gatewayRouteExample);
        if (CollectionUtils.isNotEmpty(gatewayRoutes)) {
            BaseRoute gatewayRoute = BaseRoute.builder().enable(1).build();
            int update = gatewayRouteMapper.updateByExampleSelective(gatewayRoute, gatewayRouteExample);
            RouteDefinition routeDefinition = assembleRouteDefinition(gatewayRoutes.get(0));
            redisTemplate.opsForHash().put(GatewayConstant.GATEWAY_ROUTES, routeForm.getRouteId(), JSON.toJSONString(routeDefinition));
            return update;
        } else {
            return 0;
        }
    }

    @Override
    public Integer delRoute(RouteIdForm routeForm) {
        String routeId = routeForm.getRouteId();
        BaseRouteExample gatewayRouteExample = new BaseRouteExample();
        gatewayRouteExample.createCriteria().andRouteIdEqualTo(routeId).andEnableEqualTo(1);
        BaseRoute baseRoute = MapperUtil.getFirstOrNull(gatewayRouteMapper.selectByExample(gatewayRouteExample));
        if (Objects.nonNull(baseRoute)) {
            BaseRoute gatewayRoute = BaseRoute.builder().enable(0).build();
            int update = gatewayRouteMapper.updateByExampleSelective(gatewayRoute, gatewayRouteExample);
            redisTemplate.opsForHash().delete(GatewayConstant.GATEWAY_ROUTES, baseRoute.getRouteId());
            return update;
        } else {
            return 0;
        }
    }

    @Override
    public void baseRouteRefresh() {
        redisTemplate.delete(GatewayConstant.GATEWAY_ROUTES);
        List<BaseRoute> baseRoutes = findListAll();
        HashMap<String, String> map = new HashMap<>();
        baseRoutes.stream().forEach(item -> map.put(item.getRouteId(), JSON.toJSONString(assembleRouteDefinition(item))));
        redisTemplate.opsForHash().putAll(GatewayConstant.GATEWAY_ROUTES, map);
    }

    /**
     * 转换为RouteDefinition类型
     *
     * @param gatewayRoute
     * @return
     */
    private RouteDefinition assembleRouteDefinition(BaseRoute gatewayRoute) {
        RouteDefinition definition = new RouteDefinition();
        //设定routeId
        definition.setId(gatewayRoute.getRouteId());
        String url;
        //判断uri是否为注册中心服务
        if (0 == gatewayRoute.getType()) {
            //为注册中心服务
            url = "lb://" + gatewayRoute.getUri();
        } else {
            url = gatewayRoute.getUri();
        }
        //设定URI
        URI uri = UriComponentsBuilder.fromUriString(url).build().toUri();
        definition.setUri(uri);

        //设定order
        Integer order = gatewayRoute.getOrder();
        definition.setOrder(order);

        // Filters
        List<FilterDefinition> fdList = new ArrayList<>();
        //获取路径是否去掉前缀
        Integer stripPrefix = gatewayRoute.getStripPrefix();
        //去掉前缀
        if (0 != stripPrefix) {
            //添加去掉前缀过滤器
            FilterDefinition stripFilter = new FilterDefinition();
            Map<String, String> stripParams = new HashMap<>(8);
            stripFilter.setName("StripPrefix");
            stripParams.put("_genkey_0", gatewayRoute.getStripPrefix().toString());
            stripFilter.setArgs(stripParams);
            fdList.add(stripFilter);
        }
//        //添加跳过认证过滤器
//        FilterDefinition cacheFilter = new FilterDefinition();
//        Map<String, String> cacheParams = new HashMap<>(8);
//        cacheFilter.setName("Skip");
//        cacheFilter.setArgs(cacheParams);
//        fdList.add(cacheFilter);
//        //添加认证过滤器
//        FilterDefinition authFilter = new FilterDefinition();
//        Map<String, String> authParams = new HashMap<>(8);
//        authFilter.setName("Auth");
//        authFilter.setArgs(authParams);
//        fdList.add(authFilter);
        //获取额外filter
        String filters = gatewayRoute.getFilters();
        if (null != filters) {
            Object parse = JSON.parseObject(filters);
            JSONArray filtersArray = (JSONArray) parse;
            List<FilterDefinition> list = JSONObject.parseArray(filtersArray.toJSONString(), FilterDefinition.class);
            fdList.addAll(list);
        }
        //设定filters
        definition.setFilters(fdList);

        //Predicates
        List<PredicateDefinition> pdList = new ArrayList<>();
        PredicateDefinition predicate = new PredicateDefinition();
        Map<String, String> predicateParams = new HashMap<>(8);
        predicate.setName("Path");
        predicateParams.put("pattern", gatewayRoute.getPath());
        predicate.setArgs(predicateParams);
        pdList.add(predicate);
        //获取额外Predicates
        String predicates = gatewayRoute.getPredicates();
        if (null != predicates) {
            Object parse = JSON.parseObject(predicates);
            JSONArray filtersArray = (JSONArray) parse;
            List<PredicateDefinition> list = JSONObject.parseArray(filtersArray.toJSONString(), PredicateDefinition.class);
            pdList.addAll(list);
        }
        //设定Predicates
        definition.setPredicates(pdList);

        System.out.println("definition:" + JSON.toJSONString(definition));
        return definition;
    }
}
