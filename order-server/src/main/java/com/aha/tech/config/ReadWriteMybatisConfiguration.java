package com.aha.tech.config;

import com.aha.tech.base.constants.ApplicationConstants;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import tk.mybatis.spring.annotation.MapperScan;

import static com.aha.tech.base.constants.ApplicationConstants.SCAN_READWRITE_MAPPER_XML_PATH;

/**
 * @Author: luweihong
 * @Date: 2018/7/27
 */
@Configuration
@ConditionalOnProperty(name = "readwritedb.enable", havingValue = "on")
@AutoConfigureAfter(ReadWriteJdbcConfiguration.class)
@MapperScan(basePackages = {ApplicationConstants.SCAN_MAAPPER_BASE_PACKAGE},
        sqlSessionFactoryRef = "readwriteSqlSessionFactory")
public class ReadWriteMybatisConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadWriteMybatisConfiguration.class);

    @Resource
    private DataSource readwriteDataSource;

    @Primary
    @Qualifier("readwriteSqlSessionFactory")
    @Bean(name = "readwriteSqlSessionFactory")
    public SqlSessionFactory readwriteSqlSessionFactory() throws Exception {
        LOGGER.info("readwriteSqlSessionFactory init completed !");
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(readwriteDataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(SCAN_READWRITE_MAPPER_XML_PATH));

        return bean.getObject();
    }

    @Primary
    @Qualifier("readwriteSqlSessionTemplate")
    @Bean(name = "readwriteSqlSessionTemplate")
    public SqlSessionTemplate readwriteSqlSessionTemplate(@Qualifier("readwriteSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 使用读写库的事务 直接@Transactional即可
     */
    @Primary
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager readwriteTransactionManager() {
        return new DataSourceTransactionManager(readwriteDataSource);
    }
//
//    @Bean(name = "transactionTemplate")
//    public TransactionTemplate transactionTemplate(@Qualifier("transactionManager")
//                                                    DataSourceTransactionManager transactionManager) {
//        return new TransactionTemplate(transactionManager);
//    }

}
