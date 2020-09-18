//package com.zy.apps.common.config;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
//
///**
// * @author ZY
// */
//@Configuration
//public class MongoDbConfigurer {
//    @Bean
//    @ConditionalOnMissingBean
//    public MongoCustomConversions mongoCustomConversions() {
//        List<Converter> converters = new ArrayList<>();
//        converters.add((Converter<BigDecimal, Double>) bigDecimal -> bigDecimal.doubleValue());
//        converters.add((Converter<Double, BigDecimal>) val -> new BigDecimal(val));
//        return new MongoCustomConversions(converters);
//    }
//}