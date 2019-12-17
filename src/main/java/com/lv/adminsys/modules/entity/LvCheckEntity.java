package com.lv.adminsys.modules.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
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
     * 请假ID
     */
    private String lvId;

}
