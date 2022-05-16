package com.msb.dongbao.portal.controller.pms;

import com.msb.dongbao.common.base.constant.CommonConstants;
import com.msb.dongbao.pms.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @date: 2020/6/23/18:46
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Component
@Slf4j
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    private ICategoryService categoryService;

    @Override
    public void run(String... args) throws Exception {
        long start = System.currentTimeMillis();
        log.info("调用类目列表接口开始");
        categoryService.listCategory(CommonConstants.CATEGORY_PARENT_SIZE, CommonConstants.CATEGORY_CHILD_SIZE);
        log.info("调用类目列表接口结束,耗时: {} ms",(System.currentTimeMillis()-start));

    }
}
