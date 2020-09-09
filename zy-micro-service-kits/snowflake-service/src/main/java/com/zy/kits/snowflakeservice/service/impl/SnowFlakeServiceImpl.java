package com.zy.kits.snowflakeservice.service.impl;

import com.zy.apps.common.kits.RedisKit;
import com.zy.apps.common.utils.AssertUtil;
import com.zy.kits.snowflakeservice.domain.entity.CommonKeys;
import com.zy.kits.snowflakeservice.domain.entity.CommonKeysExample;
import com.zy.kits.snowflakeservice.domain.form.GetCodeForm;
import com.zy.kits.snowflakeservice.domain.form.OrderIdForm;
import com.zy.kits.snowflakeservice.domain.form.SnowFlakeForm;
import com.zy.kits.snowflakeservice.domain.vo.CodeVo;
import com.zy.kits.snowflakeservice.mapper.CommonKeysMapper;
import com.zy.kits.snowflakeservice.service.SnowFlakeService;
import com.zy.kits.snowflakeservice.utils.IdWorker;
import com.zy.kits.snowflakeservice.utils.MD5Util;
import com.zy.kits.snowflakeservice.utils.OrderId;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Description:
 * @Author: ZY
 * @Date: 2018-12-19
 * @Time: 10:25
 */
@Service
public class SnowFlakeServiceImpl implements SnowFlakeService {
    private final String keyValue= "key:value:";
    private CommonKeysMapper commonKeysMapper;
    private RedisKit redisKit;

    public SnowFlakeServiceImpl(CommonKeysMapper commonKeysMapper, RedisKit redisKit) {
        this.commonKeysMapper = commonKeysMapper;
        this.redisKit = redisKit;
    }

    /**
     * 雪花算法生成全局唯一id
     */
    @Override
    public String getIdentifier(SnowFlakeForm snowFlakeForm) {
        AssertUtil.forbidden(null == snowFlakeForm.getPrefix(), "老兄不能传null,必传字段prefix为空字符串");
        return snowFlakeForm.getPrefix() + new IdWorker(1, 1).nextId();
    }

    /**
     * 派码器生成全局唯一id
     *
     * @param getCodeForm
     * @return
     */
    @Override
    public CodeVo getCode(GetCodeForm getCodeForm) {
        String key = getCodeForm.getPrefix() + "|" + getCodeForm.getParameter();
        String keyRedis = getCodeForm.getPrefix() + ":" + getCodeForm.getParameter();
        CommonKeysExample commonKeysExample = new CommonKeysExample();
        commonKeysExample.createCriteria().andKeyNameEqualTo(key).andDeletedAtIsNull();
        List<CommonKeys> commonKeys = commonKeysMapper.selectByExample(commonKeysExample);
        if (CollectionUtils.isEmpty(commonKeys)) {
            String keyValue = new IdWorker(1, 1).nextId() + "";
            String md5Code = MD5Util.getMD5Code(key);
            CommonKeys commonKeys1 = CommonKeys.builder()
                    .keyName(key)
                    .keyValue(keyValue)
                    .uniqueKey(md5Code)
                    .build();
            commonKeysMapper.insertSelective(commonKeys1);
            redisKit.set(keyRedis, commonKeys1.getKeyValue(), 86400);
            return CodeVo.builder().value(keyValue).build();
        } else if (redisKit.get(keyRedis) == null) {
            redisKit.set(keyRedis, commonKeys.get(0).getKeyValue(), 86400);
        }
        return CodeVo.builder().value(redisKit.get(keyRedis)).build();
    }

    /**
     * 派码器生成全局唯一id 有序 Order
     *
     * @param orderIdForm
     * @return
     */
    @Override
    public String orderCode(OrderIdForm orderIdForm) {
        return orderIdForm.getPrefix() + OrderId.getOrderNo();
    }
}
