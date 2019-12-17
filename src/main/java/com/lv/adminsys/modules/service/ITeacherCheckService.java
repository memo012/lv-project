package com.lv.adminsys.modules.service;

import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.modules.entity.LvLeaveEntity;
import com.lv.adminsys.modules.vo.leave.CompleteTaskRequest;

import java.util.List;

/**
 * @Author: qiang
 * @ClassName: ITeacherCheckService
 * @Description: 审核功能接口
 * @Date: 2019/11/15 下午7:36
 * @Version: 1.0
 **/
public interface ITeacherCheckService {

    /**
     *  查办代理
     * @param userId 审批人ID
     * @return
     */
    JSONResult queryTaskByUserId(String userId);

    /**
     *  完成审批
     *  @param taskRequest 审核请求类
     *  @return
     */
    JSONResult completeTask(CompleteTaskRequest taskRequest);

}
