package com.lv.adminsys.modules.controller.web;

import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.modules.service.web.IWebUserService;
import com.lv.adminsys.modules.vo.web.user.UserMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Qiang
 * @version 1.0
 * @description 个人信息controller
 * @date 2019/12/28 下午3:55
 **/
@RestController
@RequestMapping("api/v1/user/")
public class WebUserController {

    @Autowired
    private IWebUserService iWebUserService;

    /**
     *  个人信息
     * @param
     * @return
     */
    @GetMapping("/findUserMessage")
    public JSONResult findUserMessage(@RequestParam("userName") String userName){
        return iWebUserService.findUserMessage(userName);
    }

    /**
     *  修改个人信息
     * @param
     * @return
     */
    @PostMapping("/updateUserMessage")
    public JSONResult updateUserMessage(@RequestBody UserMessageVO userMessageVO){
        return iWebUserService.updateUserMessage(userMessageVO);
    }

    /**
     *  修改密码
     * @param userNum
     * @param password
     * @return
     */
    @GetMapping("/updatePwd")
    public JSONResult updatePwd(@RequestParam("userNum") String userNum, @RequestParam("password") String password){
        return iWebUserService.updateUserPassword(userNum, password);
    }
}
