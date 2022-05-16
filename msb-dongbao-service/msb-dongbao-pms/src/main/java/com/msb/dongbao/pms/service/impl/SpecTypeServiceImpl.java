package com.msb.dongbao.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.db.dao.SpecTypeDao;
import com.msb.dongbao.pms.model.entity.SpecType;
import com.msb.dongbao.pms.service.ISpecTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 规格类别 服务实现类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-17
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Service
@Slf4j
public class SpecTypeServiceImpl extends ServiceImpl<SpecTypeDao, SpecType> implements ISpecTypeService {

    @Autowired
    private SpecTypeDao specTypeDao;

    /**
     * 新增规格类别
     * @param name
     */
    @Override
    public ResultWrapper addSpecType(String name){

        SpecType specType = new SpecType();
        specType.setName(name);
        specType.setEnabled(false);
        specType.setGmtCreate(System.currentTimeMillis());
        specTypeDao.insert(specType);
        return ResultWrapper.getSuccessBuilder().msg("新增规格类别成功").build();
    }

}
