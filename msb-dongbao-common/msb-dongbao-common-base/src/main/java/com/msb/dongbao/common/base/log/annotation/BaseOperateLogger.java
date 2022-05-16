package com.msb.dongbao.common.base.log.annotation;

import com.msb.dongbao.common.base.enums.LogLevelEnum;
import com.msb.dongbao.common.base.enums.LogScopeEnum;
import com.msb.dongbao.common.base.enums.LogTypeEnum;
import com.msb.dongbao.common.base.enums.OperationTypeEnum;
import com.msb.dongbao.common.base.enums.system.ModuleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年05月30日 00时22分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Target({ElementType.METHOD}) //注解作用于方法级别
@Retention(RetentionPolicy.RUNTIME) //运行时起作用
public @interface BaseOperateLogger {

    /**
     * 是否输出日志
     */
    boolean loggable() default true;

    /**
     * 日志描述
     * 可使用占位符获取参数: {{tel}}
     */
    String description() default "";

    /**
     * 模块名称
     */
    ModuleEnum module() default ModuleEnum.MODULE_COMMON;

    /**
     * 操作类型(enum):主要是 select,insert,update,delete
     */
    OperationTypeEnum operationType() default OperationTypeEnum.UNKNOWN;

    /**
     * 日志请求类型
     */
    LogTypeEnum type() default LogTypeEnum.WEB;

    /**
     * 日志级别
     */
    LogLevelEnum level() default LogLevelEnum.INFO;

    /**
     * 日志输出范围,用于标记需要记录的日志信息范围，包含入参、返回值等
     * ALL-入参和出参, BEFORE-入参, AFTER-出参
     */
    LogScopeEnum scope() default LogScopeEnum.ALL;

    /**
     * 是否存入数据库
     */
    boolean db() default false;

    /**
     * 是否输出到控制台
     */
    boolean console() default true;

}
