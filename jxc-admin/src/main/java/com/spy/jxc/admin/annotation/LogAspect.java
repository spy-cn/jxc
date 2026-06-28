package com.spy.jxc.admin.annotation;

import com.spy.jxc.admin.common.jwt.CurrentUser;
import com.spy.jxc.admin.entity.Log;
import com.spy.jxc.admin.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 系统操作日志 AOP 切面：拦截标注了 @SysLog 的方法，
 * 在业务正常执行后记录操作日志。
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final LogService logService;

    @Around("@annotation(sysLog)")
    public Object around(ProceedingJoinPoint point, SysLog sysLog) throws Throwable {
        Object result = point.proceed();
        try {
            Log logEntity = new Log();
            logEntity.setLogType(sysLog.type());
            logEntity.setContent(sysLog.value());
            logEntity.setLogDate(new Date());
            logEntity.setUserId(CurrentUser.getUserId());
            logService.save(logEntity);
        } catch (Exception e) {
            log.warn("记录操作日志失败: {}", e.getMessage());
        }
        return result;
    }
}
