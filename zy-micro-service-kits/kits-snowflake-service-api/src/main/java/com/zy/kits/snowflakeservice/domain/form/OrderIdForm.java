package com.zy.kits.snowflakeservice.domain.form;

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
 * @Time: 11:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderIdForm {
    /**
     * 默认为"" 需要前缀需要加上"prefix":"order"
     */
    private String prefix;
}
