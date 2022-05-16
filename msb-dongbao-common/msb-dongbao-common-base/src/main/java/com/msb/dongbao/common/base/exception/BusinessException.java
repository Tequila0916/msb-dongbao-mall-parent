package com.msb.dongbao.common.base.exception;

import com.msb.dongbao.common.base.enums.ErrorCodeEnum;

/**
 * 自定义业务异常类
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月15日 18时58分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public class BusinessException extends RuntimeException{

    private int errorCode;

    public BusinessException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.msg());
        this.errorCode = errorCodeEnum.code();
    }

    public BusinessException(ErrorCodeEnum errorCodeEnum,Throwable cause) {
        super(errorCodeEnum.msg(),cause);
        this.errorCode = errorCodeEnum.code();
    }

    public BusinessException(String message) {
        super(message);
        this.errorCode = 500;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = 500;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
