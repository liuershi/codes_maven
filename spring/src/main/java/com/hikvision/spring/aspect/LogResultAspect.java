package com.hikvision.spring.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author zhangwei151
 * @description LogResultAspect
 * @date 2021/10/18 13:56
 */
@Aspect
public class LogResultAspect {

    @AfterReturning(pointcut = "com.hikvision.spring.aspect.SystemArchitecture.businessService()", returning = "result")
    public void logResult(Object result) {
        System.out.println("方法执行完成后打印，打印结果：" + result.toString());
    }
}
