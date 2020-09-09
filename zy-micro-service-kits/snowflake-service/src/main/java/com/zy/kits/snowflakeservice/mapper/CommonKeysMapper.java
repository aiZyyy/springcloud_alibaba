package com.zy.kits.snowflakeservice.mapper;

import com.zy.kits.snowflakeservice.domain.entity.CommonKeys;
import com.zy.kits.snowflakeservice.domain.entity.CommonKeysExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with Mybatis Generator Plugin
 *
 * @author MiaoWoo
 * Created on 2019/04/15 10:37.
 */
@Repository
public interface CommonKeysMapper {
    long countByExample(CommonKeysExample example);

    int deleteByExample(CommonKeysExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommonKeys record);

    int insertSelective(CommonKeys record);

    List<CommonKeys> selectByExample(CommonKeysExample example);

    CommonKeys selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommonKeys record, @Param("example") CommonKeysExample example);

    int updateByExample(@Param("record") CommonKeys record, @Param("example") CommonKeysExample example);

    int updateByPrimaryKeySelective(CommonKeys record);

    int updateByPrimaryKey(CommonKeys record);
}