package com.msb.dongbao.common.base.log.aspect;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.msb.dongbao.common.base.log.annotation.BaseOperateLogger;
import com.msb.dongbao.common.base.log.bean.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年05月30日 01时10分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Aspect
@Slf4j
public class OperateLogAspect {

    @Pointcut("@annotation(com.msb.dongbao.common.base.log.annotation.BaseOperateLogger)")
    public void operationLog(){}

    /**
     * 环绕增强，相当于MethodInterceptor
     */
    @Around("operationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        long time = System.currentTimeMillis();
        try {
            result =  joinPoint.proceed();
            time = System.currentTimeMillis() - time;
            return result;
        } finally {
            try {
                // 方法执行完成后增加日志
                addOperationLog(joinPoint,result,time);
            }catch (Exception e){
                log.error("LogAspect 操作失败：{}" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void addOperationLog(JoinPoint joinPoint, Object result, long time){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        OperationLog operationLog = new OperationLog();
        operationLog.setRunTime(time);
        Gson gson = new GsonBuilder().create();
        operationLog.setReturnValue(gson.toJson(result));
        operationLog.setId(UUID.randomUUID().toString());
        operationLog.setArgs(gson.toJson(joinPoint.getArgs()));
        operationLog.setCreateTime(System.currentTimeMillis());
        operationLog.setMethod(signature.getDeclaringTypeName() + "." + signature.getName());
        operationLog.setUserId("#{currentUserId}");
        operationLog.setUserName("#{currentUserName}");
        BaseOperateLogger annotation = signature.getMethod().getAnnotation(BaseOperateLogger.class);
        if(annotation != null){
            operationLog.setLevel(annotation.level().getValue());
            operationLog.setDescribe(getDetail(((MethodSignature)joinPoint.getSignature()).getParameterNames(),joinPoint.getArgs(),annotation));
            operationLog.setOperationType(annotation.operationType().getValue());
            operationLog.setModuleId(annotation.module().getValue());
        }

       log.info("记录日志:{}" ,operationLog.toString());
//        operationLogService.insert(operationLog);
    }

    /**
     * 对当前登录用户和占位符处理
     * @param argNames 方法参数名称数组
     * @param args 方法参数数组
     * @param annotation 注解信息
     * @return 返回处理后的描述
     */
    private String getDetail(String[] argNames, Object[] args, BaseOperateLogger annotation){

        Map<Object, Object> map = new HashMap<>(4);
        for(int i = 0;i < argNames.length;i++){
            map.put(argNames[i],args[i]);
        }

        String description = annotation.description();
        try {
            description = "'" + "#{currentUserName}" + "'=》" + annotation.description();
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                Object k = entry.getKey();
                Object v = entry.getValue();
                description = description.replace("{{" + k + "}}",new Gson().toJson(v));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return description;
    }

    @Before("operationLog()")
    public void doBeforeAdvice(JoinPoint joinPoint){
        log.info("进入方法前执行.....");
    }

    /**
     * 处理完请求，返回内容
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "operationLog()")
    public void doAfterReturning(Object ret) {
        log.info("方法的返回值 : " + ret);
    }

    /**
     * 后置异常通知
     */
    @AfterThrowing("operationLog()")
    public void throwss(JoinPoint jp){
       log.info("方法异常时执行.....");
    }


    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     */
    @After("operationLog()")
    public void after(JoinPoint jp){
        System.out.println("方法最后执行.....");
    }

}
