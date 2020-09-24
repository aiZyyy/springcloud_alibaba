package com.zy.kits.snowflakeservice.service;

import com.zy.kits.snowflakeservice.domain.form.GetCodeForm;
import com.zy.kits.snowflakeservice.domain.form.OrderIdForm;
import com.zy.kits.snowflakeservice.domain.form.SnowFlakeForm;
import com.zy.kits.snowflakeservice.domain.vo.CodeVo;

/**
 * Created with IntelliJ IDEA
 *
 * @Description:
 * @Author: ZY
 * @Date: 2018-12-19
 * @Time: 10:23
 */
public interface SnowFlakeService {
    /**
     * 雪花算法生成全局唯一id
     */
    String getIdentifier(SnowFlakeForm snowFlakeForm);

    /**
     * 派码器生成全局唯一id 无序
     *
     * @param getCodeForm
     * @return
     */
    CodeVo getCode(GetCodeForm getCodeForm);

    /**
     * 派码器生成全局唯一id 有序 Order
     *
     * @param orderIdForm
     * @return
     */
    String orderCode(OrderIdForm orderIdForm);
}
