package com.lv.adminsys.modules.controller;

import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.common.constant.LvException;
import com.lv.adminsys.modules.entity.LvLeaveEntity;
import com.lv.adminsys.modules.service.IUserLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: qiang
 * @ClassName: UserLeaveController
 * @Description: 用户请假操作controller
 * @Date: 2019/11/9 下午3:11
 * @Version: 1.0
 **/
@RestController
@RequestMapping("api/v1/leave/")
public class UserLeaveController {

    @Autowired
    private IUserLeaveService userLeaveService;


    /**
     * 学生提交申请
     * @param lvLeaveEntity
     * @return
     */
    @PostMapping("applyLeave")
    public JSONResult applyLeave(@RequestBody LvLeaveEntity lvLeaveEntity){
        boolean b = userLeaveService.queryIsExist(lvLeaveEntity.getLvUserNum());
        if(!b){
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        boolean result = userLeaveService.applyLeave(lvLeaveEntity);
        if(result){
            return JSONResult.ok();
        }
        return JSONResult.errorMsg("新增失败");
    }

    /**
     *  查询学生请假状态(详情)
     * @param lvId 请假ID
     * @return
     */
    @GetMapping("queryUserIsPass")
    public JSONResult queryUserIsPass(@RequestParam("lv_id") String lvId){
        LvLeaveEntity result = userLeaveService.queryUserIsPass(lvId);
        if(result == null){
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        return JSONResult.ok(result);
    }


    /**
     *  查询学生请假列表
     * @param userNum 学生学号
     * @return
     */
    @GetMapping("queryUserApplyList")
    public JSONResult queryUserApplyList(@RequestParam("lv_user_num") String userNum){
        boolean b = userLeaveService.queryIsExist(userNum);
        if (!b) {
            return JSONResult.build(402, LvException.ErrorMsg.REQUEST_PARAM_ERROR, null);
        }
        List<LvLeaveEntity> result = userLeaveService.queryUserApplyList(userNum);
        if(result == null){
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        return JSONResult.ok(result);
    }


    /**
     *  查询学生请假进程(详情)
     * @param lvId
     * @return
     */
    @GetMapping("queryUserProcess")
    public JSONResult queryUserProcess(@RequestParam("lv_id") String lvId){
        return userLeaveService.queryUserProcess(lvId);
    }


}
