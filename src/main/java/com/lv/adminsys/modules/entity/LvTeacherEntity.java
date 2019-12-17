package com.lv.adminsys.modules.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * <p>
 *  老师实体类
 * </p>
 *
 * @author qiang
 * @since 2019-10-30
 */
@Data
@TableName(value = "lv_teacher")
public class LvTeacherEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识符
     */
    @TableId("lv_teacher_id")
    private String lvTeacherId;

    /**
     * 老师姓名
     */
    private String lvTeacherName;

    /**
     * 老师工号
     */
    private String lvTeacherNum;

    /**
     * 老师密码
     */
    private String lvTeacherPassword;

    /**
     * 老师手机号
     */
    private String lvTeacherPhone;

    /**
     * 老师办公室地址
     */
    private String lvTeacherLocation;

    /**
     * 老师工作时间
     */
    private String lvTeacherWorkTime;

    /**
     * 老师创建时间
     */
    private String lvTeacherCreateTime;

    /**
     * 老师(领导)角色
     */
    private Integer lvRole;

    /**
     *  角色
     */
    @TableField(exist = false)
    private Set<LvRolesEntity> roles;

}
