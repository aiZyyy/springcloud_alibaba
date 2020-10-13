package com.zy.base.routeservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by fishCoder
 *
 * @Author ZY
 * @Date 2020/10/13 11:10
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkipRoute {
    private Integer id;

    /**
     * 跳过认证路径名称
     */
    private String name;

    /**
     * 跳过认证路径
     */
    private String url;

    /**
     * 备注
     */
    private String remark;
}