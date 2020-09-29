package com.zy.kits.marketservice.domain.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Author: ZY
 * @Date: 2019/8/8 18:24
 * @Version 1.0
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppIdForm {

    @NotBlank(message = "appId不能为空")
    private String appId;
}
