package com.lv.adminsys.modules.service;

import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.modules.entity.LvLeaveEntity;
import com.lv.adminsys.modules.entity.LvUserEntity;
import com.lv.adminsys.modules.entity.VO.UserStatusEntity;

import java.util.List;

/**
 * @Author: qiang
 * @ClassName: UserLeave
 * @Description: 学生请假相关操作接口
 * @Date: 2019/11/9 下午2:41
 * @Version: 1.0
 **/
public interface IUserLeaveService {

    /**
     *  学生提交请假申请
     * @param lvLeaveEntity
     * @return
     */
    boolean applyLeave(LvLeaveEntity lvLeaveEntity);

    /**
     *  查看学生申请列表
     * @param lvUserNum 学生学号
     * @return
     */
    List<LvLeaveEntity> queryUserApplyList(String lvUserNum);

    /**
     *  请假详情查看
     * @param lvId 请假ID
     * @return
     */
    LvLeaveEntity queryUserIsPass(String lvId);

    /**
     *  学生请假进度
     * @param lvId 请假id
     */
    JSONResult queryUserProcess(String lvId);

    /**
     *  学生是否存在
     * @param userNum 学生学号
     * @return
     */
    boolean queryIsExist(String userNum);

}
