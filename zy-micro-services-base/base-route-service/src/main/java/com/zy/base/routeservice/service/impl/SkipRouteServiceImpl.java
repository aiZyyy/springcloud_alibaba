package com.zy.base.routeservice.service.impl;

import com.zy.apps.common.constant.GatewayConstant;
import com.zy.apps.common.kits.DozerBeanKit;
import com.zy.apps.common.utils.AssertUtil;
import com.zy.apps.common.utils.MapperUtil;
import com.zy.base.routeservice.domain.entity.SkipRoute;
import com.zy.base.routeservice.domain.entity.SkipRouteExample;
import com.zy.base.routeservice.domain.form.RouteSkipAddForm;
import com.zy.base.routeservice.domain.form.RouteSkipDelForm;
import com.zy.base.routeservice.mapper.SkipRouteMapper;
import com.zy.base.routeservice.service.SkipRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @Author: ZY
 * @Date: 2019/10/15 17:23
 * @Version 1.0
 * @Description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SkipRouteServiceImpl implements SkipRouteService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private SkipRouteMapper skipRouteMapper;

    /**
     * 可跳过验签路径添加
     *
     * @param routeSkipForm
     */
    @Override
    public void skipRouteAdd(RouteSkipAddForm routeSkipForm) {
        String skipRoute = routeSkipForm.getUrl();
        redisTemplate.opsForHash().put(GatewayConstant.SKIP_ROUTES, skipRoute, "0");
        SkipRoute route = getSkipRoute(skipRoute);
        if (Objects.nonNull(route)) {
            AssertUtil.forbidden(false, "已存在相应路径,请勿重复添加!");
        }
        SkipRoute map = DozerBeanKit.map(routeSkipForm, SkipRoute.class);
        skipRouteMapper.insertSelective(map);
    }

    private SkipRoute getSkipRoute(String skipRoute) {
        SkipRouteExample skipRouteExample = new SkipRouteExample();
        skipRouteExample.createCriteria().andUrlEqualTo(skipRoute);
        return MapperUtil.getFirstOrNull(skipRouteMapper.selectByExample(skipRouteExample));
    }

    /**
     * 可跳过验签路径删除
     *
     * @param routeSkipDelForm
     */
    @Override
    public void skipRouteDel(RouteSkipDelForm routeSkipDelForm) {
        String skipRoute = routeSkipDelForm.getUrl();
        redisTemplate.opsForHash().delete(GatewayConstant.SKIP_ROUTES, skipRoute);
        SkipRoute route = getSkipRoute(skipRoute);
        if (Objects.nonNull(route)) {
            SkipRouteExample skipRouteExample = new SkipRouteExample();
            skipRouteExample.createCriteria().andUrlEqualTo(skipRoute);
            skipRouteMapper.deleteByExample(skipRouteExample);
        }
    }

    @Override
    public void skipRouteRefresh() {
        redisTemplate.delete(GatewayConstant.SKIP_ROUTES);
        List<SkipRoute> skipRoutes = skipRouteMapper.selectByExample(new SkipRouteExample());
        HashMap<String, String> map = new HashMap<>();
        skipRoutes.stream().forEach(item -> map.put(item.getUrl(), "0"));
        redisTemplate.opsForHash().putAll(GatewayConstant.SKIP_ROUTES, map);
    }
}
