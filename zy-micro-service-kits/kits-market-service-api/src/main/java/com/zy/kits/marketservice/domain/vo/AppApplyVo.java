package com.zy.kits.marketservice.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZY
 * @Date: 2019/8/6 15:04
 * @Version 1.0
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppApplyVo {

    private String appId;

    private String appPrivateKey;

    private String appPublicKey;

}
