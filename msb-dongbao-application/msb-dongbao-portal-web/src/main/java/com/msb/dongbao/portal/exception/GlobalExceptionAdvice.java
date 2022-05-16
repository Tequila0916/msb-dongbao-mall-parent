package com.msb.dongbao.portal.exception;

import com.google.common.collect.Lists;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.common.base.enums.StateCodeEnum;
import com.msb.dongbao.common.base.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 统一异常处理
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年05月28日 16时41分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {

    @ResponseBody
    @ExceptionHandler({BusinessException.class})
    public ResultWrapper bizException(BusinessException e) {
        log.error("出现异常:{}", e);
        return ResultWrapper.builder().code(e.getErrorCode()).msg(e.getMessage()).build();
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultWrapper methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("出现异常:{}", e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        // 已经设置了快速失败，可以默认取首个错误即可
        StringBuffer sb = new StringBuffer();
        List<String> errorArr = Lists.newArrayList();
        for (ObjectError error : errors) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                errorArr.add(fieldError.getField() + fieldError.getDefaultMessage());
            } else {
                errorArr.add(error.getObjectName() + error.getDefaultMessage());
            }
        }
        String errMsg = String.join(";", errorArr.toArray(new String[]{}));
        return ResultWrapper.builder().code(StateCodeEnum.METHOD_ARGUMENT_NOT_VALID.getCode()).msg(errMsg).build();
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultWrapper constraintViolationException(ConstraintViolationException e) {
        // 拦截单个参数校验异常捕获
        log.error("出现异常:{}", e);
        // @RequestParam 参数校验失败
        StringBuffer sb = new StringBuffer();
        List<String> errorArr = Lists.newArrayList();
        for (ConstraintViolation constraint : e.getConstraintViolations()) {
            errorArr.add(constraint.getInvalidValue() + "非法" + constraint.getMessage());
        }
        String errMsg = String.join(";", errorArr.toArray(new String[]{}));
        return ResultWrapper.builder().code(StateCodeEnum.CONSTRAINT_VIOLATION.getCode()).msg(errMsg).build();
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultWrapper exception(Exception e) {
        log.error("出现异常:{}", e);
        return ResultWrapper.builder().code(StateCodeEnum.ERROR.getCode()).msg(e.getMessage()).build();
    }


    /**
     * 参数错误类型转换
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResultWrapper parameterTypeConvertException(HttpMessageConversionException e) {
        return ResultWrapper.builder().code(StateCodeEnum.ERROR.getCode()).msg(e.getMessage()).build();
    }

}
