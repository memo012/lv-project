package com.lv.adminsys.modules.controller;

import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.modules.service.IUserService;
import com.lv.adminsys.modules.vo.login.UserLoginRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Qiang
 * @version 1.0
 * @description 登录接口
 * @date 2019/12/7 下午9:19
 **/
@RestController
@RequestMapping("api/v1/login/")
public class LoginController extends BaseController {

    @Resource
    private IUserService iUserService;

    /**
     * 登录
     * @param userLoginRequest
     * @return
     */
    @PostMapping("userLogin")
    public JSONResult userLogin(@RequestBody UserLoginRequest userLoginRequest){
        return iUserService.userLogin(userLoginRequest);
    }

}
