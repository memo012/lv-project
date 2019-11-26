package com.lv.adminsys.modules.controller;

import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.modules.entity.LvLeaveEntity;
import com.lv.adminsys.modules.service.IUserLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: qiang
 * @ClassName: UserLeaveController
 * @Description: 用户请假操作controller
 * @Date: 2019/11/9 下午3:11
 * @Version: 1.0
 **/
@RestController
@RequestMapping("api/v1/leave/")
public class UserLeaveController extends BaseController {

    @Autowired
    private IUserLeaveService userLeaveService;


    /**
     * 学生提交申请
     * @param lvLeaveEntity
     * @return
     */
    @PostMapping("applyLeave")
    public JSONResult applyLeave(@RequestBody LvLeaveEntity lvLeaveEntity){
        return userLeaveService.applyLeave(lvLeaveEntity);
    }

    /**
     *  查询学生请假状态(详情)
     * @param lvId 请假ID
     * @return
     */
    @GetMapping("queryUserIsPass")
    public JSONResult queryUserIsPass(@RequestParam("lv_id") String lvId){
        return userLeaveService.queryUserIsPass(lvId);
    }


    /**
     *  查询学生请假列表
     * @param userNum 学生学号
     * @param status 状态码  --  未过期(now) 和 历史(history)
     * @return
     */
    @GetMapping("queryUserApplyList")
    public JSONResult queryUserApplyList(@RequestParam("lvUserNum") String userNum, @RequestParam("status") String status){
        return userLeaveService.queryUserApplyList(userNum, status);
    }


    /**
     *  查询学生请假进程(详情)
     * @param lvId  请假id
     * @param state 状态码    --  未过期(now) 和 历史(history)
     * @return
     */
    @GetMapping("queryUserProcess")
    public JSONResult queryUserProcess(@RequestParam("lvId") String lvId, @RequestParam("state") String state){
        return userLeaveService.queryUserProcess(lvId, state);
    }

    /**
     *  我的请假条列表(已通过的)
     * @param uerNum 学号
     * @return
     */
    @GetMapping("queryApplyListByUserNum")
    public JSONResult queryApplyListByUserNum(@RequestParam("lvUserNum") String uerNum){
        return userLeaveService.queryApplyListByUserNum(uerNum);
    }

    /**
     *  我的请假条详情
     * @param lvId  学号
     * @return
     */
    @GetMapping("queryApplyDetailByUserNum")
    public JSONResult queryApplyDetailByUserNum(@RequestParam("lvId") String lvId) {
        return userLeaveService.queryApplyDetailByUserNum(lvId);
    }


}
