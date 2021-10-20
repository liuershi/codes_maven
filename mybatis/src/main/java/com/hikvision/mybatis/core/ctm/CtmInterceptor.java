package com.hikvision.mybatis.core.ctm;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Properties;

/**
 * <p>
 * @link https://mybatis.org/mybatis-3/zh/configuration.html#plugins
 * </p>
 *
 * @author zhangwei151
 * @description 自定义实现的拦截器，可以在sql执行前后自定义业务逻辑
 * @date 2021/7/25 13:49
 */
@Intercepts(
        @Signature(
            type = Executor.class,
            method = "update",
            args = {MappedStatement.class, Object.class}
        )
)
@Slf4j
public class CtmInterceptor implements Interceptor {

    private Properties properties = new Properties();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("executor execute sql before");
        final Object result = invocation.proceed();
        log.info("executor execute sql after");
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
