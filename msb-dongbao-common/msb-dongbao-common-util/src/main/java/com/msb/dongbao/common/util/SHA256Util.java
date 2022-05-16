package com.msb.dongbao.common.util;

import java.security.MessageDigest;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @date: 2020/6/9/16:54
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public class SHA256Util {

    public static String getHash(String str){
        String specHash = null;
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(str.getBytes("UTF-8"));
            specHash = toHash(digest.digest());
        }catch (Exception e){
            e.printStackTrace();
        }
        return specHash;
    }

    private static String toHash(byte[] bytes){
        StringBuffer buffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1){
                buffer.append("0");
            }
            buffer.append(temp);
        }
        return buffer.toString();
    }
}
