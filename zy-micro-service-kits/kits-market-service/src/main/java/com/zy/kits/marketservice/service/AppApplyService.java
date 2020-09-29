package com.zy.kits.marketservice.service;

import com.zy.kits.marketservice.domain.form.AppApplyForm;
import com.zy.kits.marketservice.domain.form.AppIdForm;
import com.zy.kits.marketservice.domain.vo.AppApplyVo;
import com.zy.kits.marketservice.domain.vo.AppPublicKeyVo;

/**
 * @Author: ZY
 * @Date: 2019/8/6 15:10
 * @Version 1.0
 * @Description:
 */
public interface AppApplyService {

    AppApplyVo apply(AppApplyForm applyForm);

    AppPublicKeyVo selectPublicKey(AppIdForm appPublicKeyForm);
}
