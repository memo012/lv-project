package com.lv.adminsys.modules.service;

import com.lv.adminsys.modules.entity.LvLeaveEntity;

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
    List<LvLeaveEntity> queryTaskByUserId(String userId);

    /**
     *  完成审批
     * @param userId 用户ID
     * @param taskId    任务ID
     * @param aduit 审批结果
     * @param aduit 审批原因
     */
    void completeTask(String taskId, String userId, String aduit, String comment);

}
