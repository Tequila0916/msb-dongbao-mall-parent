package com.msb.dongbao.pms;

import com.msb.dongbao.pay.model.entity.PayTransaction;
import com.msb.dongbao.pay.service.IPayTransactionService;
import com.msb.dongbao.portal.DongBaoPortalApplication;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * 单元测试Demo
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月10日 10时20分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Transactional
@SpringBootTest(classes = DongBaoPortalApplication.class)
public class DemoTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IPayTransactionService payTransactionService;

    @BeforeAll
    public static void beforeAll(){
        System.out.println("beforeAll");
    }

    @BeforeEach
    public void beforeEach(){
        System.out.println("beforeEach");
    }

    @AfterEach
    public void afterEach(){
        System.out.println("afterEach");
    }

    @AfterAll
    public static void afterAll(){
        System.out.println("afterAll");
    }


    @Test
    @Rollback
    public void test(){
//        String forObject = restTemplate.getForObject("http://localhost:9999/api/segment/get/paylog", String.class);
//        System.out.println("分布式ID" + forObject);
        PayTransaction payTransaction = new PayTransaction();
        payTransaction.setOrderStatus(0);
        payTransaction.setTradeNo("1");
        payTransaction.setRelOrderNo("1");
        payTransaction.setTransactionNo("1");
        payTransactionService.save(payTransaction);
    }

}
