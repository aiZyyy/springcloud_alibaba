package com.zy.gateway.routeservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with Mybatis Generator Plugin
 *
 * @author MiaoWoo
 * Created on 2019/10/16 10:26.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatewayRoute {
    private Integer id;

    private String routeId;

    private String uri;

    private Integer type;

    private Integer order;

    private String path;

    private Integer stripPrefix;

    private String predicates;

    private String filters;

    private Integer enable;

    private Date createTime;

    private Date updateTime;
}