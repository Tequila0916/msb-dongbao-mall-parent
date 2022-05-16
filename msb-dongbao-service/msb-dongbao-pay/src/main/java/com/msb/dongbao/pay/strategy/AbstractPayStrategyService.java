package com.msb.dongbao.pay.strategy;

import com.msb.dongbao.pay.service.IPayStrategyService;
import com.msb.dongbao.ums.model.dto.MemberDetailsDTO;
import com.msb.dongbao.ums.model.entity.UmsMember;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 抽象支付策略
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月08日 10时23分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public abstract class AbstractPayStrategyService implements IPayStrategyService {

    /**
     * 处理支付宝回调通知
     * @param request
     * @return
     */
    public Map<String,String> parseAliPayCallback(HttpServletRequest request,boolean notify){
        Map<String,String> params = new HashMap<>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            if(notify){
                params.put(name,valueStr);
            }else{
                // 乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes(Charset.forName("ISO-8859-1")), Charset.forName("utf-8"));
                params.put(name, valueStr);
            }
        }
        return params;
    }

    /**
     * 获取当前登录用户的信息
     */
    protected UmsMember getCurrentUser() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user instanceof MemberDetailsDTO){
            MemberDetailsDTO userDetails = (MemberDetailsDTO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return  userDetails.getUmsMember();
        }
        return null;
    }

}
