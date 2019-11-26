package com.lv.adminsys.modules.vo.leave;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: qiang
 * @ClassName: LeaveDetailResponse
 * @Description: TODO
 * @Date: 2019/11/17 下午10:17
 * @Version: 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveDetailResponse {

    /**
     * 请假ID
     */
    private String lvId;

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
     * 请假进度(reject - 未通过 ing - 审批中 pass - 已通过）
     */
    private String lvStatus;

}
