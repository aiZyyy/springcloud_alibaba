package com.zy.kits.snowflakeservice.controller;

import com.zy.kits.snowflakeservice.api.SnowFlakeServiceApi;
import com.zy.kits.snowflakeservice.domain.form.GetCodeForm;
import com.zy.kits.snowflakeservice.domain.form.OrderIdForm;
import com.zy.kits.snowflakeservice.domain.form.SnowFlakeForm;
import com.zy.kits.snowflakeservice.domain.vo.CodeVo;
import com.zy.kits.snowflakeservice.service.SnowFlakeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA
 *
 * @Description:
 * @Author: ZY
 * @Date: 2018-12-19
 * @Time: 10:16
 */
@Slf4j
@RestController
public class SnowFlakeController implements SnowFlakeServiceApi {
    private SnowFlakeService snowFlakeServiceImpl;

    public SnowFlakeController(SnowFlakeService snowFlakeServiceImpl) {
        this.snowFlakeServiceImpl = snowFlakeServiceImpl;
    }

    /**
     * 雪花算法生成全局唯一id
     */
    @Override
    @PostMapping("/identifier")
    public String idEntifier(@RequestBody @Valid SnowFlakeForm snowFlakeForm) {
        return snowFlakeServiceImpl.getIdentifier(snowFlakeForm);
    }

    /**
     * 雪花算法生成全局唯一id
     */
    @Override
    @GetMapping("/identifier")
    public String getIdEntifier(@RequestBody @Valid SnowFlakeForm snowFlakeForm) {
        return snowFlakeServiceImpl.getIdentifier(snowFlakeForm);
    }

    /**
     * 派码器生成全局唯一id 无序的
     *
     * @param getCodeForm
     * @return
     */
    @Override
    @PostMapping("/getcode")
    public CodeVo getCode(@RequestBody @Valid GetCodeForm getCodeForm) {
        return snowFlakeServiceImpl.getCode(getCodeForm);
    }

    /**
     * 派码器生成全局唯一id 有序 Order
     *
     * @param orderIdForm
     * @return
     */
    @Override
    @PostMapping("/order/code")
    public String orderCode(@RequestBody @Valid OrderIdForm orderIdForm) {
        return snowFlakeServiceImpl.orderCode(orderIdForm);
    }
}
