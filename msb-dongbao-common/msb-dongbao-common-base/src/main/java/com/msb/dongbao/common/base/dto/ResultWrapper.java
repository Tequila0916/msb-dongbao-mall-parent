package com.msb.dongbao.common.base.dto;

import com.msb.dongbao.common.base.enums.StateCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="基础返回实体")
@Builder
public class ResultWrapper<T> implements Serializable {

	/**
	 * 响应提示信息
	 */
	@ApiModelProperty(name = "msg", dataType = "string", value = "响应信息")
	private String msg;
	/**
	 * 返回码：200正常，500以上为错误信息
	 */
	@ApiModelProperty(name = "getCode", dataType = "int", value = "响应码")
	private int code;
	/**
	 * 返回
	 */
	@ApiModelProperty(name = "data", dataType = "object", value = "数据内容")
	private T data;

	public static ResultWrapper.ResultWrapperBuilder getSuccessBuilder(){
		return  ResultWrapper.builder().code(StateCodeEnum.SUCCESS.getCode()).msg(StateCodeEnum.SUCCESS.msg());
	}

	public static ResultWrapper.ResultWrapperBuilder getErrorBuilder(){
		return  ResultWrapper.builder().code(StateCodeEnum.ERROR.getCode()).msg(StateCodeEnum.SUCCESS.msg());
	}
}
