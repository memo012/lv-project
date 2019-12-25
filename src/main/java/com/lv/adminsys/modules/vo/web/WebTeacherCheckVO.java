package com.lv.adminsys.modules.vo.web;

import com.lv.adminsys.common.utils.TimeUtil;
import com.lv.adminsys.modules.entity.LvLeaveEntity;
import com.lv.adminsys.modules.vo.leave.TeacherCheckResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Qiang
 * @version 1.0
 * @description TODO
 * @date 2019/12/7 下午8:04
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebTeacherCheckVO extends TeacherCheckResponse implements Serializable {

    private static final long serialVersionUID = -2234491423892427021L;

    /**
     * 亲属关系
     */
    private String lvRelativeType;

    /**
     * 亲属姓名
     */
    private String lvRelativeName;

    /**
     * 亲属电话
     */
    private String lvRelativePhone;

    /**
     *  请假理由详情
     */
    private String lvApplyDetail;

    /**
     * 请假原因（病假，事假，比赛，活动，其他）
     */
    private String lvReason;

    /**
     *  请假状态
     */
    private String lvStatus;

    public WebTeacherCheckVO(String userName, String lvUserNum, String lvBeginTime,
                             String lvEndTime, String taskId, String lvId, String lvRelativeType,
                             String lvRelativeName, String lvRelativePhone, String lvApplyDetail,
                             String lvReason, String lvStatus){
        setId(new TimeUtil().getLongTime());
        setUserName(userName);
        setLvUserNum(lvUserNum);
        setLvBeginTime(lvBeginTime);
        setLvEndTime(lvEndTime);
        setTaskId(taskId);
        setLvId(lvId);
        this.lvRelativeType = lvRelativeType;
        this.lvRelativeName = lvRelativeName;
        this.lvRelativePhone = lvRelativePhone;
        this.lvApplyDetail = lvApplyDetail;
        this.lvReason = lvReason;
        this.lvStatus = lvStatus;
    }

}
