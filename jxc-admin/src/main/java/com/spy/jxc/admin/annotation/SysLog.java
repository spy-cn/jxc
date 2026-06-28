package com.spy.jxc.admin.annotation;

import java.lang.annotation.*;

/**
 * 系统操作日志注解：标注在 Controller 方法上，由 AOP 切面自动记录操作日志
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    /** 日志类型（如：用户管理、商品管理、进货入库） */
    String type();
    /** 操作描述（如：新增用户、删除商品） */
    String value();
}
