package com.lv.adminsys.modules.vo.leave;

import com.lv.adminsys.common.utils.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Qiang
 * @version 1.0
 * @description 请假人信息详情
 * @date 2019/12/8 下午1:26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyItemResponse implements Serializable {


    private static final long serialVersionUID = 2339405723877427750L;

    /**
     *  标识符
     */
    private String id;

    /**
     * 学生学号
     */
    private String lvUserNum;

    /**
     * 学生姓名
     */
    private String lvUserName;

    /**
     * 学生手机号
     */
    private String lvUserPhone;


    /**
     * 亲属姓名
     */
    private String lvRelativeName;

    /**
     * 亲属电话
     */
    private String lvRelativePhone;

    /**
     * 请假开始时间
     */
    private String lvBeginTime;

    /**
     * 请假截止时间
     */
    private String lvEndTime;

    /**
     * 请假原因（病假，事假，比赛，活动，其他）
     */
    private String lvReason;

    /**
     *  请假长度 (一天以内, 一天以上三天以下, 三天以上一周之内, 一周以上)
     */
    private String lvLength;


    public ApplyItemResponse(String lvUserNum, String lvUserName, String lvUserPhone, String lvRelativeName,
                             String lvRelativePhone, String lvBeginTime, String lvEndTime, String lvReason,
                             String lvLength){
        this.id = new TimeUtil().getLongTime();
        this.lvUserNum = lvUserNum;
        this.lvUserName = lvUserName;
        this.lvUserPhone = lvUserPhone;
        this.lvRelativeName = lvRelativeName;
        this.lvRelativePhone = lvRelativePhone;
        this.lvBeginTime = lvBeginTime;
        this.lvEndTime = lvEndTime;
        this.lvReason = lvReason;
        this.lvLength = lvLength;
    }



}
