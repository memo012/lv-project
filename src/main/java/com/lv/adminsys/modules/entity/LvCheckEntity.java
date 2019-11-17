package com.lv.adminsys.modules.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *  老师审核表
 * </p>
 *
 * @author qiang
 * @since 2019-10-30
 */
@Data
@TableName(value = "lv_check")
public class LvCheckEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识符
     */
    @TableId("lv_check_id")
    private String lvCheckId;

    /**
     * 老师工号
     */
    private String lvTeacherNum;

    /**
     * 是否审核通过(1 -成功 0 -失败)
     */
    private Integer lvCheckPass;

    /**
     * 审批批注
     */
    private String lvCheckReason;

    /**
     * 请假ID
     */
    private String lvId;

    /**
     * 审核时间
     */
    private String lvCheckCreateTime;


}
