package com.lv.adminsys.modules.service;

import com.lv.adminsys.modules.entity.LvUserEntity;
import com.lv.adminsys.modules.vo.login.UserLoginRequest;
import com.lv.adminsys.modules.vo.login.UserLoginResponse;

/**
 * @Author: qiang
 * @ClassName: IUserService
 * @Description: 学生操作
 * @Date: 2019/11/16 下午7:02
 * @Version: 1.0
 **/
public interface IUserService {

    /**
     *  用户登录
     * @param request
     * @return
     */
    UserLoginResponse userLogin(UserLoginRequest request);

    /**
     *  判断该学生是否存在
     * @param request
     * @return
     */
    LvUserEntity userIsExit(UserLoginRequest request);

    /**
     *  用户注册
     * @param lvUserEntity
     * @return
     */
    boolean registerUser(LvUserEntity lvUserEntity);

    /**
     * 删除
     * @param userName
     * @return
     */
    int deleteByUserName(String userName);
}
