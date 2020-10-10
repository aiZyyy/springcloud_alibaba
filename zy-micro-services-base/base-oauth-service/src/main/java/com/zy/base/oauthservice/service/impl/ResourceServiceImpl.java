package com.zy.base.oauthservice.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.zy.apps.common.constant.AuthConstant;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author ZY
 * @date 2020/9/30 9:35
 * @Description:
 * @Version 1.0
 */
@Service
public class ResourceServiceImpl {
    private Map<String, List<String>> resourceRolesMap;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @PostConstruct
    public void initData() {
        resourceRolesMap = new TreeMap<>();
        resourceRolesMap.put("/demo/auth/hello", CollUtil.toList("ADMIN"));
        resourceRolesMap.put("/demo/auth/user/currentUser", CollUtil.toList("ADMIN", "TEST"));
        redisTemplate.opsForHash().putAll(AuthConstant.RESOURCE_ROLES_MAP_KEY, resourceRolesMap);
    }
}
