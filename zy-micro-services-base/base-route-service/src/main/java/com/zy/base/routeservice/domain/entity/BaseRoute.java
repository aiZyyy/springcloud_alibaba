package com.zy.base.routeservice.domain.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by fishCoder
 *
 * @Author ZY
 * @Date 2020/10/13 10:56
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseRoute {
    private Integer id;

    /**
     * 路由id
     */
    private String routeId;

    /**
     * 真实路径
     */
    private String uri;

    /**
     * 指向路径地址类型,url类型 0:注册中心服务 1:其他服务
     */
    private Integer type;

    /**
     * 优先级
     */
    private Integer order;

    /**
     * 对外路径
     */
    private String path;

    /**
     * 是否忽略前缀
     */
    private Integer stripPrefix;

    /**
     * 路由断言信息
     */
    private String predicates;

    /**
     * 路由过滤器信息
     */
    private String filters;

    /**
     * 是否启用(0:未启用,1:启用)
     */
    private Integer enable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}