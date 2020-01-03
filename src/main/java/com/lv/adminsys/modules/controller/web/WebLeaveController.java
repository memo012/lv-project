package com.lv.adminsys.modules.controller.web;

import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.modules.service.web.IWebLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * 消息显示
     * @param account  学生学号 或 老师工号
     * @return
     */
    @GetMapping("/findMessageList")
    public JSONResult findMessageList(@RequestParam("account") String account){
        return iWebLeaveService.findMessageList(account);
    }

    /**
     * 标记消息已读(单条)
     * @param lvMessageId  消息id
     * @param accountType 账号类型
     * @return
     */
    @GetMapping("/markMsgIsRead")
    public JSONResult markMsgIsRead(@RequestParam("lvMessageId") String lvMessageId,  @RequestParam("accountType") Integer accountType){
        return iWebLeaveService.markMsgIsRead(lvMessageId,accountType);
    }

    /**
     * 标记全部消息已读
     * @param lvMessageId  消息id
     * @param accountType 账号类型
     * @return
     */
    @GetMapping("/markAllMsgIsRead")
    public JSONResult markAllMsgIsRead(@RequestParam("lvMessageId") String lvMessageId,  @RequestParam("accountType") Integer accountType){
        return iWebLeaveService.markAllMsgIsRead(lvMessageId,accountType);
    }

    /**
     * 消息删除(单挑)
     * @param lvMessageId  消息id
     * @param accountType 账号类型
     * @return
     */
    @GetMapping("/markMsgDelete")
    public JSONResult markMsgDelete(@RequestParam("lvMessageId") String lvMessageId,  @RequestParam("accountType") Integer accountType){
        return iWebLeaveService.markMsgDelete(lvMessageId,accountType);
    }

    /**
     * 全部消息删除
     * @param lvMessageId  消息id
     * @param accountType 账号类型
     * @return
     */
    @GetMapping("/markAllMsgDelete")
    public JSONResult markAllMsgDelete(@RequestParam("lvMessageId") String lvMessageId,  @RequestParam("accountType") Integer accountType){
        return iWebLeaveService.markAllMsgDelete(lvMessageId,accountType);
    }


}
