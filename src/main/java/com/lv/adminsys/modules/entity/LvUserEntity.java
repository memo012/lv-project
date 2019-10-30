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
@TableName(value = "lv_user")
public class LvUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识符
     */
    @TableId("lv_user_id")
    private String lvUserId;

    /**
     * 学生学号
     */
    private String lvUserNum;

    /**
     * 学生密码
     */
    private String lvUserPassword;

    /**
     * 学生姓名
     */
    private String lvUserName;

    /**
     * 学生手机号
     */
    private String lvUserPhone;

    /**
     * 老师姓名
     */
    private String lvTeacherName;

    /**
     * 学生创建时间
     */
    private String lvUserCreateTime;


}
