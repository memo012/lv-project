package com.lv.adminsys.modules.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author qiang
 * @since 2019-10-30
 */
@Data
@TableName(value = "lv_table")
public class LvLeaveEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 请假ID
     */
    @TableId("lv_id")
    private String lvId;

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
     * 请假开始时间
     */
    private String lvBeginTime;

    /**
     * 请假天数
     */
    private Integer lvDay;

    /**
     * 请假截止时间
     */
    private String lvEndTime;

    /**
     * 请假原因（病假，事假，比赛，活动，其他）
     */
    private String lvReason;

    /**
     * 请假进度（0 - 未通过 1 - 已通过）
     */
    private Integer lvProgress = 0;

    /**
     * 上传附件
     */
    private String lvPicture;

    /**
     * 请假创建时间
     */
    private String lvCreateTime;


}
