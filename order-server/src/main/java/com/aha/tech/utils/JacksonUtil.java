package com.aha.tech.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JacksonUtil {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = SpringContextUtil.getBean("objectMapper");
    }


    /**
     * 对象转JSON
     */
    public static String objToJson(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("对象转json数据格式失败!", e);
        }
    }

    /**
     * JSON转对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToObject(String json, Class<T> clazz) {
        try {
            return (T) objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new IllegalArgumentException("json数据格式不正确,或转换失败!", e);
        }
    }

    public static <T> T jsonToObject(String json, TypeReference<T> valueTypeRef) throws IllegalArgumentException {
        try {
            return objectMapper.readValue(json, valueTypeRef);
        } catch (Exception e) {
            throw new IllegalArgumentException("json数据格式不正确,或转换失败!", e);
        }
    }

    /**
     * JSON中的某个字段的值
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToObject(String json, String key, Class<T> clazz) {
        if (json == null || json.trim().length() == 0) {
            return null;
        }
        try {
            Map<String, Object> map = jsonToObject(json, HashMap.class);
            Object val = map.get(key);
            if (val == null) {
                return null;
            }
            String subJson = objectMapper.writeValueAsString(val);
            return (T) jsonToObject(subJson, clazz);
        } catch (Exception e) {
            throw new IllegalArgumentException("json数据格式不正确,或转换失败!", e);
        }
    }

    /**
     * JSON转集合对象
     */
    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        try {
            JavaType collectionType = getCollectionType(ArrayList.class, clazz);
            return objectMapper.readValue(json, collectionType);
        } catch (Exception e) {
            throw new IllegalArgumentException("json数据格式不正确,或转换失败!", e);
        }
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static ObjectMapper getMapper() {
        return objectMapper;
    }

    public static void setMapper(ObjectMapper mapper) {
        JacksonUtil.objectMapper = mapper;
    }

}
