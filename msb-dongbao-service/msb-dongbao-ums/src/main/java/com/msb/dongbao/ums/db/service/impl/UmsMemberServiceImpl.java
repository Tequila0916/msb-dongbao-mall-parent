package com.msb.dongbao.ums.db.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.dongbao.common.base.enums.ErrorCodeEnum;
import com.msb.dongbao.common.base.exception.BusinessException;
import com.msb.dongbao.common.util.JwtTokenUtil;
import com.msb.dongbao.ums.db.dao.UmsMemberDao;
import com.msb.dongbao.ums.model.dto.MemberDetailsDTO;
import com.msb.dongbao.ums.model.dto.UmsMemberLoginParamDTO;
import com.msb.dongbao.ums.model.dto.UmsMemberRegisterParamDTO;
import com.msb.dongbao.ums.model.dto.UpdateMemberPasswordParamDTO;
import com.msb.dongbao.ums.model.entity.UmsMember;
import com.msb.dongbao.ums.service.IUmsMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-28
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Service
@Slf4j
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberDao, UmsMember> implements IUmsMemberService {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private PasswordEncoder passwordEncoder;
    


    @Override
    public UmsMember getAdminByUsername(String username) {
        UmsMember member ;
        Wrapper<UmsMember> wrapper = new QueryWrapper<UmsMember>().lambda().eq(UmsMember::getUsername,username);
        List<UmsMember> memberList = this.list(wrapper);
        if (!CollectionUtils.isEmpty(memberList)) {
            member = memberList.get(0);
            return member;
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public String login(UmsMemberLoginParamDTO umsMemberLoginParamDTO, HttpServletRequest request) {
        loginCheck(umsMemberLoginParamDTO);
        String username = umsMemberLoginParamDTO.getUsername();
        String password = umsMemberLoginParamDTO.getPassword();
        String token = null;
        //密码需要客户端加密后传递
        UserDetails userDetails = loadUserByUsername(username);
        try {
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token =jwtTokenUtil.generateToken(userDetails);
           /* updateLoginTimeByUsername(username);
            insertLoginLog(username);*/
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    /**
     * 用户注册 验证方法
     * @param umsMemberRegisterParamDTO
     */
    private void registerCheck(UmsMemberRegisterParamDTO umsMemberRegisterParamDTO){
        //滑块轨迹
        List<Integer> tracks = umsMemberRegisterParamDTO.getTracks();
        boolean b = verifyTracks(tracks);
        if(!b){
            throw new BusinessException("滑块校验失败,请重新验证");
        }
    }

    /**
     * 用户登录 验证方法
     * 可以在这里校验错误次数,锁定标志位
     * @param umsMemberLoginParamDTO
     */
    private void loginCheck(UmsMemberLoginParamDTO umsMemberLoginParamDTO){
        //滑块轨迹
        List<Integer> tracks = umsMemberLoginParamDTO.getTracks();
        boolean b = verifyTracks(tracks);
        if(!b){
            throw new BusinessException("滑块校验失败,请重新验证");
        }
        //todo 登陆的时候可以校验更多情况
    }

    /**
     * 校验滑块轨迹
     * @param datas
     * @return
     */
    private boolean verifyTracks(List<Integer> datas) {
        int sum = 0;
        for (Integer data : datas) {
            sum += data;
        }
        double avg = sum * 1.0 / datas.size();

        double sum2 = 0.0;
        for (Integer data : datas) {
            sum2 += Math.pow(data - avg, 2);
        }

        double stddev = sum2 / datas.size();
        return stddev != 0;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        //获取用户信息
        UmsMember user = getAdminByUsername(username);
        if (user != null) {
            return new MemberDetailsDTO(user);
        }
        throw new UsernameNotFoundException("用户不存在");
    }


    @Override
    public UmsMember register(UmsMemberRegisterParamDTO umsAdminParam, HttpServletRequest request) {
        //注册校验
        registerCheck(umsAdminParam);

        UmsMember umsMember = new UmsMember();
        BeanUtils.copyProperties(umsAdminParam, umsMember);
        umsMember.setCreateTime(new Date());
        umsMember.setStatus(1);
        Wrapper<UmsMember> wrapper = new QueryWrapper<UmsMember>().lambda().eq(UmsMember::getUsername,umsAdminParam.getUsername());
        List<UmsMember> userList = this.list(wrapper);
        if (userList.size() > 0) {
            //账号重复
            throw new BusinessException(ErrorCodeEnum.UMS0001010);
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsMember.getPassword());
        umsMember.setPassword(encodePassword);
        this.save(umsMember);
        return umsMember;
    }

    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public UmsMember getUserInfoById(Long id) {
        return this.getById(id);
    }


    @Override
    public int updatePassword(UpdateMemberPasswordParamDTO param) {
        @NotEmpty(message = "用户名不能为空") String username = param.getUsername();
        if(StrUtil.isEmpty(username)
                ||StrUtil.isEmpty(param.getOldPassword())
                ||StrUtil.isEmpty(param.getNewPassword())){
            return -1;
        }
        Wrapper<UmsMember> wrapper = new QueryWrapper<UmsMember>().lambda().eq(UmsMember::getUsername,username);
        List<UmsMember> userList = this.list(wrapper);
        if(CollUtil.isEmpty(userList)){
            return -2;
        }
        UmsMember umsMember = userList.get(0);
        if(!passwordEncoder.matches(param.getOldPassword(), umsMember.getPassword())){
            return -3;
        }
        umsMember.setPassword(passwordEncoder.encode(param.getNewPassword()));
        this.updateById(umsMember);
        return this.updateById(umsMember) ? 1:0;
    }
}
