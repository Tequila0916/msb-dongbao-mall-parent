package com.msb.dongbao.common.util;

import java.util.UUID;

/**
 * 随机ID工具类生成
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月09日 14时51分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public class IDUtils{

    public static String UUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

}

