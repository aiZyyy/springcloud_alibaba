package com.zy.base.routeservice.mapper;

import com.zy.base.routeservice.domain.entity.SkipRoute;
import com.zy.base.routeservice.domain.entity.SkipRouteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by fishCoder
 *
 * @Author ZY
 * @Date 2020/10/13 11:10
 */
@Repository
public interface SkipRouteMapper {
    long countByExample(SkipRouteExample example);

    int deleteByExample(SkipRouteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SkipRoute record);

    int insertSelective(SkipRoute record);

    List<SkipRoute> selectByExample(SkipRouteExample example);

    SkipRoute selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SkipRoute record, @Param("example") SkipRouteExample example);

    int updateByExample(@Param("record") SkipRoute record, @Param("example") SkipRouteExample example);

    int updateByPrimaryKeySelective(SkipRoute record);

    int updateByPrimaryKey(SkipRoute record);
}