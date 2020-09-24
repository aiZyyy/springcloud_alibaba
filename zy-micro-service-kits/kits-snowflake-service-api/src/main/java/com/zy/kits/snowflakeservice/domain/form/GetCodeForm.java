package com.zy.kits.snowflakeservice.domain.form;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA
 *
 * @Description:
 * @Author: ZY
 * @Date: 2018-12-20
 * @Time: 9:32
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCodeForm {
    @NotBlank(message = "前缀不能为空")
    private String prefix;
    @NotBlank(message = "参数不能为空")
    private String parameter;
}
