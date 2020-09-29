package com.zy.kits.marketservice.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZY
 * @Date: 2019/8/8 18:25
 * @Version 1.0
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPublicKeyVo {


    private String appPublicKey;
}
