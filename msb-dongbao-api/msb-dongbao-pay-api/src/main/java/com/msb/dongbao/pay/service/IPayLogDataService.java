package com.msb.dongbao.pay.service;

import com.msb.dongbao.pay.model.entity.PayLogData;

/**
 * 交易日志业务接口
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月17日 17时33分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface IPayLogDataService {
    /**
     * 保存交易日志
     * @param payLogData
     * @return
     */
    PayLogData saveLogData(PayLogData payLogData);
}
