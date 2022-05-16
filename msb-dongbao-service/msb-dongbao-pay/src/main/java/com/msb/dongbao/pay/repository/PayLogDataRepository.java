package com.msb.dongbao.pay.repository;

import com.msb.dongbao.pay.model.entity.PayLogData;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Component;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月08日 15时34分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Component
public interface PayLogDataRepository extends CassandraRepository<PayLogData,Long> {
}
