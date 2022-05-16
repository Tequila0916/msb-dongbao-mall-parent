package com.msb.dongbao.common.base.dto;

/**
 * 业务状态码
 * 组成描述: 错误级别(1位) + 模块标识(2位) + 具体错误码(3位)
 * 0011000
 *
 * 错误级别
 *
 * 1 服务端返回提示
 * 2 服务端不做提示
 * 3 友好提醒,俏皮表情
 *
 *  模块标识
 *
 * 01 - 用户模块
 *
 * 具体错误码:
 *
 * 1000 ~ 1999 用户权限异常
 * 3000 ~ 3999 第三方接口异常
 * 4000 ~ 4999 请求参数错误
 * 5000 ~ 5999 应用内部接口异常
 *
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月17日 09时38分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface ErrorCode {

    int USER_NO_AUTH = 1011001;
    int USER_NO_LOGIN = 1011002;
    int USER_IS_LOCKED = 1011003;

    /** 数据错误 */
    int DTAT_NOT_EXIST = 6011001;

}
