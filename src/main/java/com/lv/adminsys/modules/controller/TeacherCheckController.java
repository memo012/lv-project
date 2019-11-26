package com.lv.adminsys.modules.controller;

import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.modules.entity.LvLeaveEntity;
import com.lv.adminsys.modules.service.ITeacherCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: qiang
 * @ClassName: TeacherCheckController
 * @Description: 老师审核controller
 * @Date: 2019/11/15 下午7:33
 * @Version: 1.0
 **/
@RestController
@RequestMapping("api/v1/check/")
public class TeacherCheckController extends BaseController {

    @Autowired
    private ITeacherCheckService iTeacherCheckService;


    /**
     *  查询代办
     * @param userId 审批人ID
     * @return
     */
    @RequestMapping("/queryByTeacherId")
    public JSONResult queryByTeacherId(String userId){
        List<LvLeaveEntity> lvLeaveEntities = iTeacherCheckService.queryTaskByUserId(userId);
        return JSONResult.ok(lvLeaveEntities);
    }


    /**
     *  审核任务
     * @param taskId 任务ID
     * @param userId 用户ID
     * @param aduit 审批结果
     * @param comment 审批原因
     * @return
     */
    @RequestMapping("/completeTask")
    public JSONResult completeTask(String taskId, String userId, String aduit, String comment) {
        iTeacherCheckService.completeTask(taskId, userId, aduit, comment);
        return JSONResult.ok("操作成功");
    }

}
