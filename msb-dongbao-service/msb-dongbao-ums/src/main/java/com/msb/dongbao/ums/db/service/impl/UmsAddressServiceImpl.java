package com.msb.dongbao.ums.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.common.base.exception.BusinessException;
import com.msb.dongbao.ums.db.dao.UmsAddressDao;
import com.msb.dongbao.ums.model.dto.UmsAddressDTO;
import com.msb.dongbao.ums.model.entity.UmsAddress;
import com.msb.dongbao.ums.service.IUmsAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.msb.dongbao.common.base.enums.ErrorCodeEnum.UMS0001012;

/**
 * <p>
 * 收货地址 服务实现类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-01
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Service
@Slf4j
public class UmsAddressServiceImpl extends ServiceImpl<UmsAddressDao, UmsAddress> implements IUmsAddressService {

    @Autowired
    private UmsAddressDao addressDao;

    @Override
    public ResultWrapper<String> addAddress(UmsAddressDTO dto){
        log.info("新增收货地址 入参:【{}】",new Gson().toJson(dto));

        UmsAddress address = new UmsAddress();
        BeanCopier copier = BeanCopier.create(UmsAddressDTO.class, UmsAddress.class, false);
        copier.copy(dto,address,null);
        if (address.getDefaultAddress()){
            UmsAddress defaultAdd = addressDao.selectOne(new QueryWrapper<UmsAddress>().lambda().eq(UmsAddress::getDefaultAddress,true));
            if(defaultAdd != null){
                defaultAdd.setDefaultAddress(false);
                addressDao.updateById(defaultAdd);
            }
        }
        address.setEnabled(false);
        address.setGmtCreate(System.currentTimeMillis());
        addressDao.insert(address);
        return ResultWrapper.getSuccessBuilder().msg("新增收货地址成功").build();
    }

    @Override
    public ResultWrapper<String> deleteAddress(Integer id){
        log.info("删除收货地址 入参:【{}】",id);
        UmsAddress address = new UmsAddress();
        address.setEnabled(true);
        address.setId(Long.valueOf(id));
        addressDao.updateById(address);
        return ResultWrapper.getSuccessBuilder().msg("删除收货地址成功").build();
    }


    @Override
    public ResultWrapper<String> updateAddress(UmsAddressDTO dto){
        log.info("更新收货地址 入参:【{}】",new Gson().toJson(dto));

        UmsAddress address = new UmsAddress();
        BeanCopier copier = BeanCopier.create(UmsAddressDTO.class, UmsAddress.class, false);
        copier.copy(dto,address,null);
        address.setGmtModified(System.currentTimeMillis());
        if (address.getDefaultAddress()){
            UmsAddress defaultAdd = addressDao.selectOne(new QueryWrapper<UmsAddress>().lambda().eq(UmsAddress::getDefaultAddress,true));
            if(defaultAdd != null){
                defaultAdd.setDefaultAddress(false);
                addressDao.updateById(defaultAdd);
            }
        }
        addressDao.updateById(address);
        return ResultWrapper.getSuccessBuilder().msg("更新收货地址成功").build();
    }

    @Override
    public ResultWrapper<List<UmsAddressDTO>> listUmsAddress(String username){
        log.info("收货地址列表 入参:【{}】",username);
        List<UmsAddressDTO> dtos = Lists.newArrayList();

        List<UmsAddress> addresses = addressDao.selectList(new QueryWrapper<UmsAddress>().lambda().eq(UmsAddress::getLoginUser,username).eq(UmsAddress::getEnabled, false));
        for (UmsAddress address : addresses) {
            UmsAddressDTO dto = new UmsAddressDTO();
            BeanCopier copier = BeanCopier.create(UmsAddress.class, UmsAddressDTO.class, false);
            copier.copy(address,dto,null);
            dtos.add(dto);
        }
        return ResultWrapper.getSuccessBuilder().data(dtos).build();
    }

    @Override
    public ResultWrapper<UmsAddressDTO> detail(Integer id){
        log.info("获取收货地址详情 入参:【{}】",id);
        UmsAddressDTO dto = new UmsAddressDTO();
        UmsAddress address = addressDao.selectById(Long.valueOf(id));
        if (address == null ){
            throw new BusinessException(UMS0001012);
        }
        BeanCopier copier = BeanCopier.create(UmsAddress.class, UmsAddressDTO.class, false);
        copier.copy(address,dto,null);
        return ResultWrapper.getSuccessBuilder().data(dto).build();

    }
}
