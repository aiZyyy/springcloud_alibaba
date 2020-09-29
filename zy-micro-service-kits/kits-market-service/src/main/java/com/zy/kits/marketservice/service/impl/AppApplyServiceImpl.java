package com.zy.kits.marketservice.service.impl;

import com.zy.kits.marketservice.domain.entity.AppInfo;
import com.zy.kits.marketservice.domain.entity.AppInfoExample;
import com.zy.kits.marketservice.domain.form.AppApplyForm;
import com.zy.kits.marketservice.domain.form.AppIdForm;
import com.zy.kits.marketservice.domain.vo.AppApplyVo;
import com.zy.kits.marketservice.domain.vo.AppPublicKeyVo;
import com.zy.kits.marketservice.mapper.AppInfoMapper;
import com.zy.kits.marketservice.service.AppApplyService;
import com.zy.apps.common.kits.RedisDistributedKit;
import com.zy.apps.common.utils.AssertUtil;
import com.zy.apps.common.utils.MapperUtil;
import com.zy.gateway.checksign.common.oauth.utils.RSAUtils;
import com.zy.kits.snowflakeservice.api.SnowFlakeServiceApi;
import com.zy.kits.snowflakeservice.domain.form.SnowFlakeForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * @Author: ZY
 * @Date: 2019/8/6 15:10
 * @Version 1.0
 * @Description:
 */

@Slf4j
@Service
public class AppApplyServiceImpl implements AppApplyService {
    final String key = "MARKET:";
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private AppInfoMapper appInfoMapper;
    @Autowired
    private RedisDistributedKit redisDistributedKit;
    @Resource
    private SnowFlakeServiceApi snowFlakeServiceApi;

    private static final String STOREID = "APP_APPLY";

    @Override
    public AppApplyVo apply(AppApplyForm appApplyForm) {
        AssertUtil.requireNonNull(appApplyForm, "appApplyForm对象为空");
        AppInfoExample appInfoExample = new AppInfoExample();
        appInfoExample.createCriteria().andDeletedAtIsNull().andAppNameEqualTo(appApplyForm.getAppName());
        List<AppInfo> appInfos = appInfoMapper.selectByExample(appInfoExample);
        AssertUtil.forbidden(CollectionUtils.isNotEmpty(appInfos), "应用名称重复，请修改应用名称在进行重试！");
        // 获取锁
        String token = redisDistributedKit.acquire(appApplyForm.getAppName(), STOREID, 1000, 500);
        if (!StringUtils.isEmpty(token)) {
            //雪花算法生成唯一appId
            String appId = snowFlakeServiceApi.getIdEntifier(SnowFlakeForm.builder().prefix("app").build());
            RSAUtils.RSAKeyPair rsaKeyPair = null;
            try {
                //生成唯一的公钥,私钥
                rsaKeyPair = RSAUtils.generateKeyPair();
                AssertUtil.requireNonNull(rsaKeyPair, "秘钥生成失败");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            AppInfo appInfo = AppInfo.builder().appId(appId).appName(appApplyForm.getAppName())
                    .appPrivateKey(rsaKeyPair.getPrivateKey())
                    .appPublicKey(rsaKeyPair.getPublicKey())
                    .appStartTime(new Date())
                    .companyName(appApplyForm.getCompanyName()).build();

            //app基本信息入库
            appInfoMapper.insertSelective(appInfo);
            redisTemplate.opsForValue().set(key + appInfo.getAppId(), appInfo.getAppPublicKey());
            //释放锁
            redisDistributedKit.release(appApplyForm.getAppName(), STOREID, token);
            AppApplyVo appApplyVo = AppApplyVo.builder().appId(appId).appPrivateKey(rsaKeyPair.getPrivateKey())
                    .appPublicKey(rsaKeyPair.getPublicKey()).build();
            return appApplyVo;

        }
        return null;
    }

    @Override
    public AppPublicKeyVo selectPublicKey(AppIdForm appPublicKeyForm) {

        AppInfoExample appInfoExample = new AppInfoExample();
        appInfoExample.createCriteria().andAppIdEqualTo(appPublicKeyForm.getAppId()).andAppEndTimeIsNull();
        AppInfo appInfo = MapperUtil.getFirstOrNull(appInfoMapper.selectByExample(appInfoExample));

        return AppPublicKeyVo.builder().appPublicKey(appInfo.getAppPublicKey()).build();
    }
}
