package com.msb.dongbao.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.dongbao.pay.db.dao.PayChannelDao;
import com.msb.dongbao.pay.model.entity.PayChannel;
import com.msb.dongbao.pay.service.IPayChannelService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付方式 服务实现类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-09
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Service
public class PayChannelServiceImpl extends ServiceImpl<PayChannelDao, PayChannel> implements IPayChannelService {

}
