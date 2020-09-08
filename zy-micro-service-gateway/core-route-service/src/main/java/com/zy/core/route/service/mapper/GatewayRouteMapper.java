package com.zy.core.route.service.mapper;

import com.zy.core.route.service.domain.entity.GatewayRoute;
import com.zy.core.route.service.domain.entity.GatewayRouteExample;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with Mybatis Generator Plugin
 *
 * @author MiaoWoo
 * Created on 2019/10/16 10:26.
 */
@Repository
public interface GatewayRouteMapper {
    long countByExample(GatewayRouteExample example);

    int deleteByExample(GatewayRouteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GatewayRoute record);

    int insertSelective(GatewayRoute record);

    List<GatewayRoute> selectByExample(GatewayRouteExample example);

    GatewayRoute selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GatewayRoute record, @Param("example") GatewayRouteExample example);

    int updateByExample(@Param("record") GatewayRoute record, @Param("example") GatewayRouteExample example);

    int updateByPrimaryKeySelective(GatewayRoute record);

    int updateByPrimaryKey(GatewayRoute record);
}