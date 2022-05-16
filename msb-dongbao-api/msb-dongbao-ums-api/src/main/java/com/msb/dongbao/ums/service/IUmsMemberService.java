package com.msb.dongbao.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.dongbao.ums.model.dto.UmsMemberLoginParamDTO;
import com.msb.dongbao.ums.model.dto.UmsMemberRegisterParamDTO;
import com.msb.dongbao.ums.model.dto.UpdateMemberPasswordParamDTO;
import com.msb.dongbao.ums.model.entity.UmsMember;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 用户服务接口
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-28
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface IUmsMemberService extends IService<UmsMember> {

  /**
   * 根据用户名 获取用户信息
   * @param username
   * @return
   */
  UmsMember getAdminByUsername(String username) ;

  /**
   * 登录功能
   * @param umsMemberLoginParamDTO
   * @param request
   * @return
   */
  String login(UmsMemberLoginParamDTO umsMemberLoginParamDTO, HttpServletRequest request);

  /**
   * 获得 用户信息+用户资源权限
   * @param username
   * @return
   */
   UserDetails loadUserByUsername(String username);




  /**
   * 注册功能
   * @param umsAdminParam
   * @return
   */
  UmsMember register(UmsMemberRegisterParamDTO umsAdminParam, HttpServletRequest request);


  /**
   * 刷新token的功能
   * @param oldToken 旧的token
   */
  String refreshToken(String oldToken);

  /**
   * 根据用户id获取用户
   * @param id
   * @return
   */
  UmsMember getUserInfoById(Long id);


  /**
   * 修改密码
   * @param updatePasswordParam
   * @return
   */
  int updatePassword(UpdateMemberPasswordParamDTO updatePasswordParam);



  }
