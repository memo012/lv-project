package com.lv.adminsys.modules.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * <p>
 *  请假申请实体类
 * </p>
 *
 * @author qiang
 * @since 2019-10-30
 */
@Data
@TableName(value = "lv_leave")
public class LvLeaveEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 请假ID
     */
    @TableId("lv_id")
    private String lvId;

    /**
     * 请假人学号
     */
    private String lvUserNum;

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
     *  请假理由详情
     */
    private String lvApplyDetail;

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

    /**
     * 上传附件
     */
    private String lvPicture;

    /**
     * 请假创建时间
     */
    private String lvCreateTime;


    /**
     * 结束时间时间戳
     */
    private String lvEndTimeLong;

    /**
     *  请假长度 (一天以内, 一天以上三天以下, 三天以上一周之内, 一周以上)
     */
    private String lvLength;

    /**
     *  是否已读 0 -- 未读   1 -- 已读
     */
    private int lvBook = 0;

    /**
     * 请假流程实例id
     */
    private String lvProcessInstanceId;


    public boolean createValidate(){
        return StringUtils.isNotEmpty(lvUserNum) && StringUtils.isNotEmpty(lvRelativeType)
                && StringUtils.isNotEmpty(lvRelativeName) && StringUtils.isNotEmpty(lvRelativePhone)
                && StringUtils.isNotEmpty(lvBeginTime) && StringUtils.isNotEmpty(lvLength)
                && StringUtils.isNotEmpty(lvEndTime) && StringUtils.isNotEmpty(lvReason);
    }

}
