package com.zy.kits.snowflakeservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with Mybatis Generator Plugin
 *
 * @author MiaoWoo
 * Created on 2019/04/15 10:37.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonKeys {
    private Integer id;

    private String keyName;

    private String keyValue;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    private String uniqueKey;
}