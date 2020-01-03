package com.lv.adminsys.modules.service.web;

import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.modules.vo.web.user.UserMessageVO;

/**
 * @author Qiang
 * @version 1.0
 * @description TODO
 * @date 2019/12/28 下午4:03
 **/
public interface IWebUserService {

    /**
     *  查询用户信息
     * @param userNum
     * @return
     */
    JSONResult findUserMessage(String userNum);

    /**
     *  更新用户信息
     * @param userMessageVO
     * @return
     */
    JSONResult updateUserMessage(UserMessageVO userMessageVO);

    /**
     *  修改用户密码
     *  @param userNum
     * @param password
     * @return
     */
    JSONResult updateUserPassword(String userNum, String password);

}
