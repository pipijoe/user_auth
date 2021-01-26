package cn.les.auth.config.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

/**
 * @author Joetao
 * @time 2020/5/27 2:14 PM
 * @Email cutesimba@163.com
 */
@Aspect
@Component
@Order(1)
@Slf4j
public class AccessAspect {

    @Pointcut("execution(public * cn.les.auth.controller..*.*(..))")
    public void accessLog() {

    }

    @Around("accessLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object[] args = joinPoint.getArgs();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String uri = attributes == null ? "" : attributes.getRequest().getRequestURI();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        int spendTime = (int) (endTime - startTime);
        log.info("==access== [{}] [{}] [{}] [{}]", uri, Arrays.toString(args), result.toString(), spendTime);
        return result;
    }

}
