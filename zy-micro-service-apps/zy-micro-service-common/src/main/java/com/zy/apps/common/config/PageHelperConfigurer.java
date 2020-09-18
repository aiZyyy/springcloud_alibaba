//package com.zy.apps.common.config;
//
//import java.util.Properties;
//
//import javax.sql.DataSource;
//
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.github.pagehelper.PageInterceptor;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * Created with IntelliJ IDEA
// *
// * @author ZY
// * Created on 2018/3/31 11:03.
// */
//@Slf4j
//@Configuration
//public class PageHelperConfigurer {
//    @Bean(name = "sqlSessionFactory")
//    @ConditionalOnProperty(value = "zy-micro-service-common.page-helper.enabled", havingValue = "true", matchIfMissing = true)
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//        log.info("初始化 SqlSessionFactory 设置 PageHelper 分页插件...");
//        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource);
//        sessionFactory.setPlugins(plugins());
//        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
//        config.setCallSettersOnNulls(true);
//        sessionFactory.setConfiguration(config);
//        return sessionFactory.getObject();
//    }
//
//    private Interceptor[] plugins() {
//        //分页插件设置
//        PageInterceptor pageHelper = new PageInterceptor();
//        Properties properties = new Properties();
//        properties.setProperty("reasonable", "true");
//        properties.setProperty("supportMethodsArguments", "true");
//        properties.setProperty("offsetAsPageNum", "true");
//        pageHelper.setProperties(properties);
//        return new Interceptor[]{pageHelper};
//    }
//}
