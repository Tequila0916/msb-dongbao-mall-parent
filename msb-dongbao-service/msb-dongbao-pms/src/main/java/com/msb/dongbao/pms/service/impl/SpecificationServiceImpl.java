package com.msb.dongbao.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.db.dao.SpecificationDao;
import com.msb.dongbao.pms.db.dao.SpecificationOptionDao;
import com.msb.dongbao.pms.model.dto.SpecificationDTO;
import com.msb.dongbao.pms.model.dto.SpecificationOptionDTO;
import com.msb.dongbao.pms.model.entity.Specification;
import com.msb.dongbao.pms.model.entity.SpecificationOption;
import com.msb.dongbao.pms.service.ISpecificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 规格 服务实现类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-08
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Service
@Slf4j
public class SpecificationServiceImpl extends ServiceImpl<SpecificationDao, Specification> implements ISpecificationService {

    @Autowired
    private SpecificationDao specificationDao;

    @Autowired
    private SpecificationOptionDao specificationOptionDao;

    /**
     * 新增类目/规格/规格项
     * @param specList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultWrapper addCategorySpec(List<SpecificationDTO> specList) {
        log.info("新增规格/规格选项  入参:【{}】",new Gson().toJson(specList));

        for (SpecificationDTO spec : specList) {
            Specification specification = new Specification();
            specification.setName(spec.getName());
            specification.setRelCategoryId(spec.getRelCategoryId());
            specification.setRelSpecTypeId(spec.getRelSpecTypeId());
            specification.setGmtCreate(System.currentTimeMillis());
            specification.setEnabled(false);
            specificationDao.insert(specification);

            for (SpecificationOptionDTO optionDTO : spec.getOptionDTOS()) {
                SpecificationOption option = new SpecificationOption();
                option.setName(optionDTO.getName());
                option.setRelSpecId(specification.getId());
                option.setGmtCreate(System.currentTimeMillis());
                option.setEnabled(false);
                specificationOptionDao.insert(option);
            }
        }
        return ResultWrapper.getSuccessBuilder().msg("新增规格/规格选项成功").build();
    }
}
