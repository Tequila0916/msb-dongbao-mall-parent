package com.msb.dongbao.common.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @date: 2020/6/21/20:51
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public class SortUtils {

    public static String sort(String optionIds){
        String newOptionIds="";

        String[] split = optionIds.split(",");
        List<String> list = Arrays.asList(split);
        Collections.sort(list);
        for (int i = 0; i < list.size(); i++) {
            if(i == list.size()-1){
                newOptionIds+=list.get(i);
            }else {
                newOptionIds+=list.get(i)+",";
            }
        }
        return newOptionIds;
    }
}
