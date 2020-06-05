package com.aha.tech.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: ahaschool
 * @date: 2019-08-07 13:52
 */

public class ValueOperationsUtils {
    private final static StringRedisTemplate REDIS_TEMPLATE = SpringContextUtil.getBean(StringRedisTemplate.class);
    private final static ValueOperations<String, String> VALUE_OPERATIONS = REDIS_TEMPLATE.opsForValue();

    /**
     * Set {@code value} for {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param value
     * @see <a href="http://redis.io/commands/set">Redis Documentation: SET</a>
     */
    public static void set(String key, Object value) {
        String jsonStr = JacksonUtil.objToJson(value);
        VALUE_OPERATIONS.set(key, jsonStr);
    }

    /**
     * Set the {@code value} and expiration {@code timeout} for {@code key}.
     *
     * @param key     must not be {@literal null}.
     * @param value
     * @param timeout
     * @param unit    must not be {@literal null}.
     * @see <a href="http://redis.io/commands/setex">Redis Documentation: SETEX</a>
     */
    public static void set(String key, Object value, long timeout, TimeUnit unit) {
        String jsonStr = JacksonUtil.objToJson(value);
        VALUE_OPERATIONS.set(key, jsonStr, timeout, unit);
    }

    /**
     * Get the value of {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="http://redis.io/commands/get">Redis Documentation: GET</a>
     */
    @Nullable
    public static <T> T get(String key, Class<T> clazz) {
        String valueStr = VALUE_OPERATIONS.get(key);
        if (StringUtils.isBlank(valueStr)) {
            return null;
        }
        return JacksonUtil.jsonToObject(valueStr, clazz);
    }

    public static <T> List<T> getList(String key, Class<T> clazz) {
        String valueStr = VALUE_OPERATIONS.get(key);
        if (StringUtils.isBlank(valueStr)) {
            return null;
        }
        return JacksonUtil.jsonToList(valueStr, clazz);
    }


}
