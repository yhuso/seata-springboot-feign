package com.aha.tech.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import tk.mybatis.spring.annotation.MapperScan;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * @Author: luweihong
 * @Date: 2019/11/15
 */
@Component
public class MyBatisMapperScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.aha.tech.model.mapper");

        return mapperScannerConfigurer;
    }

    @MapperScan("com.aha.tech.model.mapper")
    @ConditionalOnMissingBean(MyBatisMapperScannerConfig.class)
    @Configuration
    public static class AnnotationMapperScan {
    }

}
