package com.zy.gateway.core.routeservice.domain.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Author: ZY
 * @Date: 2019/8/14 14:11
 * @Version 1.0
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteIdForm {

    @NotBlank(message = "路由id不能为空")
    private String routeId;
}
