package com.lv.adminsys.modules.service;

import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.modules.entity.LvTeacherEntity;
import com.lv.adminsys.modules.vo.leave.ApplyItemResponse;

/**
 * @author Qiang
 * @version 1.0
 * @description  老师服务接口
 * @date 2019/12/7 下午10:14
 **/
public interface ITeacherService {

    /**
     *  通过工号查询
     * @param teacherNum 工号
     * @return
     */
    LvTeacherEntity findUserMsgByTeacherNum(String teacherNum);

    /**
     * 查询学生请假详情
     * @param lvId 请假id
     * @return
     */
    JSONResult findApplyDetail(String lvId);

    /**
     * 查询审核过的列表
     * @param teacherNum 审核人工号
     * @return
     */
    JSONResult findCompleteApplyItem(String teacherNum);


}
