package com.lv.adminsys.modules.controller;

import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.modules.entity.LvUserEntity;
import com.lv.adminsys.modules.service.IUserService;
import com.lv.adminsys.modules.vo.login.UserLoginRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: qiang
 * @ClassName: UserController
 * @Description: 学生操作
 * @Date: 2019/11/16 下午7:00
 * @Version: 1.0
 **/
@RestController
@RequestMapping("api/v1/user/")
public class UserController extends BaseController {

    @Resource
    private IUserService iUserService;

    /**
     *  注册
     * @param lvUserEntity
     * @return
     */
    @PostMapping("register/userRegister")
    public JSONResult userRegister(@RequestBody LvUserEntity lvUserEntity){
        boolean b = iUserService.registerUser(lvUserEntity);
        if(b){
            return JSONResult.ok();
        }
        return JSONResult.errorMsg("是;失败");
    }

    /**
     * 必须有Admin角色才能删除
     * @param userName
     */
    @DeleteMapping("/deleteByUserName")
    public void deleteByUserName(String userName){
        iUserService.deleteByUserName(userName);
    }

}
