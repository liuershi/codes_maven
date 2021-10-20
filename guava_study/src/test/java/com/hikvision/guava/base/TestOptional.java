package com.hikvision.guava.base;

import com.google.common.base.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author zhangwei151
 * @description TestOptional
 * @date 2021/7/21 16:29
 */
@Slf4j
public class TestOptional {

    /**
     * 简单测试Optional的使用
     */
    @Test
    public void testSimpleUseOptional() {
        Optional<Integer> optional = Optional.of(10);
        log.info("does it exist {}", optional.isPresent());
        log.info("this value is {}", optional.get());
    }

    @Test
    public void testUserValueIsNull() {
        Optional<Object> optional = Optional.of(null);
        optional.isPresent();
    }

    @Test
    public void testUserValueIsEmpty() {
        Optional<Object> optional = Optional.absent();
        log.info("does it exist {}", optional.isPresent());
        log.info("this value is {}", optional.get());
    }

    @Test
    public void testFromNullable() {
        Optional<Object> nullable = Optional.fromNullable(null);
        log.info("does it exist {}", nullable.isPresent());
        log.info("this value is {}", nullable.orNull());
        Optional<String> fromNullable = Optional.fromNullable("success");
        log.info("does it exist {}", fromNullable.isPresent());
        if (fromNullable.isPresent()) {
            log.info("this value is {}", fromNullable.get());
        }
    }
}
