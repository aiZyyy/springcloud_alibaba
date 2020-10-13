package com.zy.base.routeservice.domain.form;

/**
 * @Author: ZY
 * @Date: 2019/9/23 16:13
 * @Version 1.0
 * @Description:
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteSkipAddForm {

    private String name;

    @NotBlank(message = "跳过路径不能为空")
    private String url;

    private String remark;
}
