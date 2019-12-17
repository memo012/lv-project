package com.lv.adminsys.modules.service;

import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.modules.entity.LvLeaveEntity;
import com.lv.adminsys.modules.entity.LvUserEntity;
import com.lv.adminsys.modules.entity.VO.UserStatusEntity;
import com.lv.adminsys.modules.vo.leave.LeaveDetailResponse;

import java.text.ParseException;
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
    JSONResult applyLeave(LvLeaveEntity lvLeaveEntity);

    /**
     *  查看学生申请列表
     * @param lvUserNum 学生学号
     * @param status 学生学号
     * @return
     */
    JSONResult queryUserApplyList(String lvUserNum, String status);

    /**
     *  请假详情查看
     * @param lvId 请假ID
     * @return
     */
    JSONResult queryUserIsPass(String lvId);

    /**
     *  学生请假进度
     * @param lvId 请假id
     * @param state 查看方式(now --  未过期   history -- 历史)
     */
    JSONResult queryUserProcess(String lvId, String state);

    /**
     *  学生是否存在
     * @param userNum 学生学号
     * @return
     */
    boolean queryIsExist(String userNum);

    /**
     *  查询我的请假条(列表)
     * @param uerNum
     * @return
     */
    JSONResult queryApplyListByUserNum(String uerNum);

    /**
     *  请假表(详情)
     * @param lvId 学号
     * @return
     */
    JSONResult queryApplyDetailByUserNum(String lvId);

}
