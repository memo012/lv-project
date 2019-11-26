package com.lv.adminsys.modules.vo.leave;

import com.baomidou.mybatisplus.annotation.TableId;
import com.lv.adminsys.common.utils.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: qiang
 * @ClassName: LeaveItemResponse
 * @Description: TODO
 * @Date: 2019/11/22 下午8:03
 * @Version: 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveItemResponse implements Serializable {

    private static final long serialVersionUID = 5035339138388693914L;

    private String id;

    /**
     * 请假开始时间
     */
    private String lvBeginTime;

    /**
     * 请假ID
     */
    private String lvId;

    /**
     * 请假截止时间
     */
    private String lvEndTime;

    /**
     * 请假原因（病假，事假，比赛，活动，其他）
     */
    private String lvReason;

    /**
     * 请假创建时间
     */
    private String lvCreateTime;

    public LeaveItemResponse(String lvBeginTime, String lvEndTime, String lvReason, String lvCreateTime, String lvId){
        this.id = new TimeUtil().getLongTime();
        this.lvBeginTime = lvBeginTime;
        this.lvEndTime = lvEndTime;
        this.lvReason = lvReason;
        this.lvCreateTime = lvCreateTime;
        this.lvId = lvId;
    }


}
