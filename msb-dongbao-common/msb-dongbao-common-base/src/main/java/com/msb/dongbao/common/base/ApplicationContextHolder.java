package com.msb.dongbao.common.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户上下文
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月18日 16时41分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public class ApplicationContextHolder {

    private static  final ThreadLocal<SessionUser> threadLocal = new ThreadLocal<>();

    public static void putSessionUser(SessionUser currentUserInfo){
        if(threadLocal.get() == null){
            threadLocal.set(currentUserInfo);
        }
    }

    public static SessionUser getSessionUserInfo(){
        return threadLocal.get();
    }


    public static void clear(){
        threadLocal.remove();
    }

    @Data
    public static class SessionUser implements Serializable {


        /**用户token */
        private String token;

        /**用户ID */
        private Long id;
        /**
         * 用户唯一编号
         */
        private String userCode;

        /**
         * 邮箱
         */
        private String email;

        /**
         * 手机号
         */
        private String phone;

        /**
         * 用户名
         */
        private String username;

        /**
         * 密码
         */
        private String password;

        /**
         * 最后一次登录时间
         */
        private Integer lastLoginAt;

        /**
         * 最后一次登录ip
         */
        private String lastLoginIpAt;

    }
}
