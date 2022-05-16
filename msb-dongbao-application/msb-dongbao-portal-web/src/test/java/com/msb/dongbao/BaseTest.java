package com.msb.dongbao;

import com.msb.dongbao.portal.DongBaoPortalApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测试基类
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月17日 16时11分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Transactional
@SpringBootTest(classes = DongBaoPortalApplication.class)
public class BaseTest {
}
