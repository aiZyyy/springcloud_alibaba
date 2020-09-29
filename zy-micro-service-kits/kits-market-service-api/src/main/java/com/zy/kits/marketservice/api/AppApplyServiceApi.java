package com.zy.kits.marketservice.api;

import com.zy.kits.marketservice.domain.form.AppApplyForm;
import com.zy.kits.marketservice.domain.form.AppIdForm;
import com.zy.kits.marketservice.domain.vo.AppApplyVo;
import com.zy.kits.marketservice.domain.vo.AppPublicKeyVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @Author: ZY
 * @Date: 2019/8/6 14:52
 * @Version 1.0
 * @Description: 应用申请服务
 */
@FeignClient("kits-market-service")
public interface AppApplyServiceApi {

    /**
     * 应用申请接口
     * @param applyForm
     * @return
     */
    @PostMapping("/app/apply")
    AppApplyVo apply(@RequestBody @Valid AppApplyForm applyForm);

    /**
     * 根据APPId查询用户公钥
     */
    @PostMapping("/app/selectpublickey")
    AppPublicKeyVo selectPublicKey(@RequestBody @Valid AppIdForm appPublicKeyForm);
}
