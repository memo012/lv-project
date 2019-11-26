package com.lv.adminsys.modules.vo.leave;

import com.lv.adminsys.common.utils.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: qiang
 * @ClassName: ApplyDetailResponse
 * @Description: TODO
 * @Date: 2019/11/21 下午7:50
 * @Version: 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyDetailResponse implements Serializable {

    private static final long serialVersionUID = -4862332402084270843L;

    private String id;
    /**
     * 请假原因（病假，事假，比赛，活动，其他）
     */
    private String lvReason;

    /**
     *  请假开始时间
     */
    private String lvBeginTime;

    /**
     *  请假截止时间
     */
    private String lvEndTime;

    /**
     *  亲属电话
     */
    private String lvRelativePhone;


    /**
     *  请假长度 (一天以内, 一天以上三天以下, 三天以上一周之内, 一周以上)
     */
    private String lvLength;

    public ApplyDetailResponse(String lvReason, String lvBeginTime, String lvEndTime, String lvRelativePhone, String lvLength){
        this.id = new TimeUtil().getLongTime();
        this.lvBeginTime = lvBeginTime;
        this.lvEndTime = lvEndTime;
        this.lvReason = lvReason;
        this.lvRelativePhone = lvRelativePhone;
        this.lvLength = lvLength;
    }

}
