package com.aha.tech;

import com.google.common.base.Preconditions;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import io.micrometer.core.instrument.MeterRegistry;
import tk.mybatis.mapper.autoconfigure.MapperAutoConfiguration;

@EnableAsync
//@EnableApolloConfig
@EnableDiscoveryClient
@EnableFeignClients
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication(exclude = {MybatisAutoConfiguration.class,DataSourceAutoConfiguration.class,
        MapperAutoConfiguration.class, MongoAutoConfiguration.class,MongoDataAutoConfiguration.class})
public class Application {

    protected static Logger logger = LogManager.getLogger(Application.class);


    public static void main(String[] args) {
        String profile = System.getProperty("spring.profiles.active");
        Assert.notNull(profile, "请指定 [-Dspring.profiles.active]");
        SpringApplication app = new SpringApplication(Application.class);
        try {
            ConfigurableApplicationContext configurableApplicationContext = app.run(args);
            Environment env = configurableApplicationContext.getEnvironment();
            validateProfiles(env, profile);
            printServerInfo(env);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> configurer(
            @Value("${spring.application.name}") String applicationName) {
        return (registry) -> registry.config().commonTags("application", applicationName);
    }

    /**
     * 校验-Dspring.profiles.active 的值是否合法
     */
    private static void validateProfiles(Environment env, String inputProfile) {
        Preconditions.checkNotNull(env.getActiveProfiles(), "请指定 [-Dspring.profiles.active]");
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        Preconditions.checkArgument(profiles.contains(inputProfile), "输入的-Dspring.profiles.active 与 有效的 active不服");
    }

    /**
     * 启动输出信息
     */
    private static void printServerInfo(Environment env) throws IOException {
        String appBanner = StreamUtils.copyToString(new ClassPathResource("app-banner.txt").getInputStream(),Charset.defaultCharset());

        String applicationName = env.getProperty("spring.application.name");
        String servletContextPath = env.getProperty("server.servlet.context-path");
        String serverPort = env.getProperty("server.port");


        logger.info(appBanner, applicationName,
                StringUtils.isEmpty(env.getProperty("server.ssl.key-store")) ? "http" : "https",
                serverPort, StringUtils.defaultString(servletContextPath),

                StringUtils.isEmpty(env.getProperty("server.ssl.key-store")) ? "http" : "https",
                InetAddress.getLocalHost().getHostAddress(),
                serverPort,
                StringUtils.defaultString(servletContextPath),

                org.springframework.util.StringUtils.arrayToCommaDelimitedString(env.getActiveProfiles()),
                env.getProperty("PID"), Charset.defaultCharset());
    }

}

