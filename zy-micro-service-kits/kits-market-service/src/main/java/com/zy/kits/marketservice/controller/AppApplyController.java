package com.zy.kits.marketservice.controller;

import com.zy.kits.marketservice.api.AppApplyServiceApi;
import com.zy.kits.marketservice.domain.form.AppApplyForm;
import com.zy.kits.marketservice.domain.form.AppIdForm;
import com.zy.kits.marketservice.domain.vo.AppApplyVo;
import com.zy.kits.marketservice.domain.vo.AppPublicKeyVo;
import com.zy.kits.marketservice.service.AppApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: ZY
 * @Date: 2019/8/6 15:08
 * @Version 1.0
 * @Description:
 */
@RestController
public class AppApplyController implements AppApplyServiceApi {

    @Autowired
    private AppApplyService appApplyService;

    @Override
    @PostMapping("/app/apply")
    public AppApplyVo apply(@RequestBody @Valid AppApplyForm applyForm) {
        return appApplyService.apply(applyForm);
    }

    @Override
    @PostMapping("/app/selectpublickey")
    public AppPublicKeyVo selectPublicKey(@RequestBody @Valid AppIdForm appPublicKeyForm) {
        return appApplyService.selectPublicKey(appPublicKeyForm);
    }
}
