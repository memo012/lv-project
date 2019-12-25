package com.lv.adminsys.modules.controller;

import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.modules.entity.LvLeaveEntity;
import com.lv.adminsys.modules.service.ITeacherCheckService;
import com.lv.adminsys.modules.service.ITeacherService;
import com.lv.adminsys.modules.vo.leave.CompleteTaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @Resource
    private ITeacherCheckService iTeacherCheckService;

    @Resource
    private ITeacherService iTeacherService;


    /**
     *  查询代办
     * @param userId 审批人ID
     * @return
     */
    @GetMapping("/queryByTeacherId")
    public JSONResult queryByTeacherId(@RequestParam("teacherId") String userId){
        return iTeacherCheckService.queryTaskByUserId(userId);
    }


    /**
     *  审核任务
     * @return
     */
    @PostMapping("/completeTask")
    public JSONResult completeTask(@RequestBody CompleteTaskRequest taskRequest) {
        return iTeacherCheckService.completeTask(taskRequest);
    }

    /**
     *  被审核人请假详情
     * @return
     */
    @GetMapping("/applyDetail")
    public JSONResult handle(@RequestParam("lvId") String lvId){
        return iTeacherService.findApplyDetail(lvId);
    }

    /**
     *  审核人的历史审核列表
     * @param teacherNum 学生工号
     * @return
     */
    @GetMapping("/historyMess")
    public JSONResult historyMess(@RequestParam("teacherNum") String teacherNum){
        return iTeacherService.findCompleteApplyItem(teacherNum);
    }

}
