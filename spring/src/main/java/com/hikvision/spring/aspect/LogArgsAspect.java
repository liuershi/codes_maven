package com.hikvision.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Arrays;

/**
 * @author zhangwei151
 * @description LogArgsAspect
 * @date 2021/10/18 11:05
 */
@Aspect
public class LogArgsAspect {

    @Before(value = "com.hikvision.spring.aspect.SystemArchitecture.businessService()")
    public void logArgs(JoinPoint joinPoint) {
        System.out.println("方法执行前打印，参数为：" + Arrays.toString(joinPoint.getArgs()));
    }
}
