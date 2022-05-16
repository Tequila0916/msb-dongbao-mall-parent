package com.msb.dongbao;

import com.google.gson.Gson;
import com.msb.dongbao.pms.model.dto.SpecOptionNameDTO;
import com.msb.dongbao.pms.model.dto.SpecTypeDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年07月16日 16时05分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public class Test {

    public static void main(String[] args) {
        List<SpecTypeDTO> types = new ArrayList<>();
        SpecTypeDTO specType = new SpecTypeDTO();
        specType.setSpecType("主体");

        List<SpecOptionNameDTO> list = new ArrayList<>();
        SpecOptionNameDTO name = new SpecOptionNameDTO();
        name.setName("颜色");
        name.setValue("玫瑰金");
        list.add(name);

        name = new SpecOptionNameDTO();
        name.setName("屏幕尺寸");
        name.setValue("6.8");
        list.add(name);

        name = new SpecOptionNameDTO();
        name.setName("手机内存");
        name.setValue("128G");
        list.add(name);
        specType.setList(list);

        types.add(specType);

        Gson gson = new Gson();
        System.out.println(gson.toJson(types));
    }
}
