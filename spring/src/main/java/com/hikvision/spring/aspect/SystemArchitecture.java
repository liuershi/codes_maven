package com.hikvision.spring.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * <p>
 *     spring建议见PointCup定义在一起，在任何需要使用的地方直接引用。
 * </p>
 *
 * @author zhangwei151
 * @description 定义切点
 * @date 2021/10/18 10:57
 */
@Aspect
public class SystemArchitecture {

    // controller 层
    @Pointcut("within(com.hikvision.spring.controller..*)")
    public void inWebLayer() {}

    // service 层
    @Pointcut("within(com.hikvision.spring.service..*)")
    public void inServiceLayer() {}

    // dao 层
    @Pointcut("within(com.hikvision.spring.dao..*)")
    public void inDataAccessLayer() {}

    // service 实现，注意这里指的是方法实现，其实通常也可以使用 bean(*ServiceImpl)
    @Pointcut("execution(* com.hikvision.spring.service.impl.*.*(..))")
    public void businessService() {}
}
