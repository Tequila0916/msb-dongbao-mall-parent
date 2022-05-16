package com.msb.dongbao.pms.service;

import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.model.dto.HomeResultDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * 首页Service
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年07月21日 10时40分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface IHomeService {

    /**
     * 首页商品信息查询
     * @param request
     * @return
     */
    ResultWrapper<HomeResultDTO> homeResult(HttpServletRequest request);
}
