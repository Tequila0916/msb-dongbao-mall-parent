package com.msb.dongbao.portal.controller.ums;


import com.msb.dongbao.common.base.ApplicationContextHolder;
import com.msb.dongbao.common.base.constant.CommonConstants;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.common.base.exception.BusinessException;
import com.msb.dongbao.common.util.BeanUtils;
import com.msb.dongbao.common.util.LoginVerification;
import com.msb.dongbao.ums.model.dto.UmsMemberLoginParamDTO;
import com.msb.dongbao.ums.model.dto.UmsMemberRegisterParamDTO;
import com.msb.dongbao.ums.model.entity.UmsMember;
import com.msb.dongbao.ums.service.IUmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户管理 前端控制器
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-28
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@RestController
@Api(value = "后台用户管理 控制器", tags = "后台用户管理 控制器")
@RequestMapping("/admin")
@Validated
@Slf4j
public class UmsMemberController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private IUmsMemberService umsMemberService;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper<UmsMember> register(@RequestBody UmsMemberRegisterParamDTO user, HttpServletRequest request) {
        UmsMember umsMember = umsMemberService.register(user, request);
        return ResultWrapper.getSuccessBuilder().data(umsMember).build();
    }

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper login(@RequestBody UmsMemberLoginParamDTO umsMemberLoginParamDTO, HttpServletRequest request) {
        String token = umsMemberService.login(umsMemberLoginParamDTO, request);
        if (token == null) {
            throw new BusinessException("用户名或密码错误");
        }
        HttpSession session = request.getSession();

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("jwtToken", token);
        tokenMap.put("jwtTokenHead", tokenHead);
        tokenMap.put("username", umsMemberLoginParamDTO.getUsername());
        tokenMap.put("token", session.getId());
        tokenMap.put("userCode", "codemsb");
        ApplicationContextHolder.SessionUser sessionUser = new ApplicationContextHolder.SessionUser();
        BeanUtils.copyBean(tokenMap, sessionUser);
        session.setAttribute(CommonConstants.SESSION_USER_KEY, sessionUser);

        return ResultWrapper.getSuccessBuilder().data(tokenMap).build();
    }

    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = umsMemberService.refreshToken(token);
        if (refreshToken == null) {
            throw new BusinessException("token已经过期");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return ResultWrapper.getSuccessBuilder().data(tokenMap).build();
    }

    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper logout() {
        return ResultWrapper.getSuccessBuilder().data(true).build();
    }


    @ApiOperation(value = "验证手机号功能")
    @RequestMapping(value = "/checkPhone", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper checkPhone() {
        return ResultWrapper.getSuccessBuilder().data(false).build();
    }


    @ApiOperation(value = "获得验证码")
    @RequestMapping(value = "/getPic", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper<String> getPic(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //设置类型
        response.setContentType("image/jpeg");

        String picBase64 = LoginVerification.getPicBase64();
        String rand = LoginVerification.getRandAndClear();
        HttpSession session = request.getSession(true);
        log.info("session,{}", session.getId());
        //设置session使其进行效验
        session.setAttribute(LoginVerification.ATU_NAME, rand);

        return ResultWrapper.getSuccessBuilder().data(picBase64).build();
    }

}
