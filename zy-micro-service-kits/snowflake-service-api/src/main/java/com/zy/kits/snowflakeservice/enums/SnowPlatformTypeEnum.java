package com.zy.kits.snowflakeservice.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created with IDEA
 *
 * @Description: TODO
 * @Author:ZY
 * @Date:2019/4/18
 * @Time:15:07
 */
public enum SnowPlatformTypeEnum {

    QYWECHAT(0, "4001", "QyWechat"),
    MOBILE(1,"3005", "Mobile"),
    OA(2, "3003","OA"),
    QQ(3, "3004","QQ"),
    WECHAT(4,"3002", "Wechat"),
    ALIPAY(5, "3001","alipay");
    
    private Integer id;
    private String platform;
    private String desc;
    
    SnowPlatformTypeEnum(Integer id, String platform, String desc) {
        this.id = id;
        this.platform = platform;
        this.desc = desc;
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getPlatform() {
        return platform;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public static Map<Integer, SnowPlatformTypeEnum> enumMap = new HashMap<>();
    public static Map<String, SnowPlatformTypeEnum> statusEnumMap = new HashMap<>();
    
    static {
        for (SnowPlatformTypeEnum value : SnowPlatformTypeEnum.values()) {
            enumMap.put(value.id, value);
            statusEnumMap.put(value.platform, value);
        }
    }
    
    public static SnowPlatformTypeEnum valueOf(Integer type) {
        return Optional.ofNullable(enumMap.get(type)).orElseThrow(() -> new IllegalArgumentException("未知的SnowPlatformTypeEnum类型：" + type + "!"));
    }
    
    public static SnowPlatformTypeEnum valueOfStatus(String type) {
        return Optional.ofNullable(statusEnumMap.get(type)).orElseThrow(() -> new IllegalArgumentException("未知的SnowPlatformTypeEnum类型：" + type + "!"));
    }
}
