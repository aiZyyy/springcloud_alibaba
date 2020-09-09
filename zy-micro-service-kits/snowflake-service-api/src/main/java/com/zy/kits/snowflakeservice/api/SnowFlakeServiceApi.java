package com.zy.kits.snowflakeservice.api;


import com.zy.kits.snowflakeservice.domain.form.GetCodeForm;
import com.zy.kits.snowflakeservice.domain.form.OrderIdForm;
import com.zy.kits.snowflakeservice.domain.form.SnowFlakeForm;
import com.zy.kits.snowflakeservice.domain.vo.CodeVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA
 *
 * @Description:
 * @Author: ZY
 * @Date: 2018-12-19
 * @Time: 14:08
 */
@FeignClient("snowflake-service")
public interface SnowFlakeServiceApi {
    /**
     * 雪花算法生成全局唯一id
     */
    @PostMapping("/identifier")
    String idEntifier(@RequestBody @Valid SnowFlakeForm snowFlakeForm);

    /**
     * 雪花算法生成全局唯一id
     */
    @GetMapping("/identifier")
    String getIdEntifier(@RequestBody @Valid SnowFlakeForm snowFlakeForm);

    /**
     * 派码器生成全局唯一id 无序的
     *
     * @param getCodeForm
     * @return
     */
    @PostMapping("/getcode")
    CodeVo getCode(@RequestBody @Valid GetCodeForm getCodeForm);

    /**
     * 派码器生成全局唯一id 有序 Order
     *
     * @param orderIdForm
     * @return
     */
    @PostMapping("/order/code")
    String orderCode(@RequestBody @Valid OrderIdForm orderIdForm);
}
