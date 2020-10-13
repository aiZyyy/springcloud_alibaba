package com.zy.base.routeservice.mapper;

import com.zy.base.routeservice.domain.entity.BaseRoute;
import com.zy.base.routeservice.domain.entity.BaseRouteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by fishCoder
 *
 * @Author ZY
 * @Date 2020/10/13 10:56
 */
@Repository
public interface BaseRouteMapper {
    long countByExample(BaseRouteExample example);

    int deleteByExample(BaseRouteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BaseRoute record);

    int insertSelective(BaseRoute record);

    List<BaseRoute> selectByExample(BaseRouteExample example);

    BaseRoute selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BaseRoute record, @Param("example") BaseRouteExample example);

    int updateByExample(@Param("record") BaseRoute record, @Param("example") BaseRouteExample example);

    int updateByPrimaryKeySelective(BaseRoute record);

    int updateByPrimaryKey(BaseRoute record);
}