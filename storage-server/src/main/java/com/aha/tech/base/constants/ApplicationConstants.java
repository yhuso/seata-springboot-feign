package com.aha.tech.base.constants;

/**
 * @Author: luweihong
 * @Date: 2018/7/25
 * application 级别的应用常量
 */
public class ApplicationConstants {

    public static final String BASE_PACKAGE = "com.aha.tech";

    public static final String SCAN_BASE_PACKAGE_ENTITY_PACKAGE = "com.aha.tech.dto.entity";

    public static final String SCAN_READWRITE_MAPPER_XML_PATH = "classpath*:mapper/*Mapper.xml";

    public static final String SCAN_MAAPPER_BASE_PACKAGE = "com.aha.tech.model.mapper";

    public static final String SPRING_PROFILE_DEV = "dev";

    public static final String SPRING_PROFILE_TEST = "test";

    public static final String SPRING_PROFILE_TEST2 = "test2";

    public static final String SPRING_PROFILE_PRODUCTION = "prod";


    public static final String[] AVAILABLE_PROFILES = new String[]{SPRING_PROFILE_TEST,
            SPRING_PROFILE_PRODUCTION, SPRING_PROFILE_TEST2, SPRING_PROFILE_DEV};

    public interface CommonConfig {
        /**
         * 分页默认光标
         */
        String PAGE_CURSOR = "1";
        /**
         * 分页默认显示页数
         */
        int PAGE_LIMIT = 10;
        /**
         * 有效
         */
        Byte STATUS_1 = 1;
        /**
         * 无效
         */
        Byte STATUS_2 = 2;
    }
}
