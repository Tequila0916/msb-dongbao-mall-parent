package com.msb.dongbao.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.ums.model.dto.UmsAddressDTO;
import com.msb.dongbao.ums.model.entity.UmsAddress;

import java.util.List;

/**
 * <p>
 * 收货地址 服务类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-01
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface IUmsAddressService extends IService<UmsAddress> {

  /**
   * 新增收货地址
   * @param dto
   * */
  ResultWrapper<String> addAddress(UmsAddressDTO dto);

  /**
   * 删除收货地址
   * @param id
   * */
  ResultWrapper<String> deleteAddress(Integer id);

  /**
   * 更新收货地址
   * @param dto
   * */
  ResultWrapper<String> updateAddress(UmsAddressDTO dto);

  /**
   * 查询收货地址列表
   * */
  ResultWrapper<List<UmsAddressDTO>> listUmsAddress(String username);

  /**
   * 查询收货地址详情
   * */
  ResultWrapper<UmsAddressDTO> detail(Integer id);
}
