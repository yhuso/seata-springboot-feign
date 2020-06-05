package com.aha.tech.utils;

import com.aha.tech.commons.utils.BeanUtil;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class DataUtils {

    /**
     * list 中 对象转换
     *
     * @param sources
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> List<T> listConverter(List<?> sources, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sources)) {
            return Collections.emptyList();
        }
        return sources.stream()
                .map(source -> beanConverter(source, targetClass))
                .collect(Collectors.toList());
    }

    /**
     * Bean copy
     *
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T beanConverter(Object source, Class<T> targetClass) {
        return BeanUtil.beanCopyIgnoreNullValue(source, targetClass);
    }


}
