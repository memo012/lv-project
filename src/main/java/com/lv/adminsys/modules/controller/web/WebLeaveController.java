package com.lv.adminsys.modules.controller.web;

import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.modules.service.web.IWebLeaveService;
import com.lv.adminsys.modules.vo.web.WebTeacherCheckDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Qiang
 * @version 1.0
 * @description TODO
 * @date 2019/12/25 下午5:13
 **/
@RestController
@RequestMapping("api/v1/web/")
public class WebLeaveController {

    @Autowired
    private IWebLeaveService iWebLeaveService;

    /**
     *
     * @param
     * @return
     */
    @GetMapping("/findMessageList")
    public JSONResult findMessageList(@RequestParam("account") String account){
        return iWebLeaveService.findMessageList(account);
    }


}
