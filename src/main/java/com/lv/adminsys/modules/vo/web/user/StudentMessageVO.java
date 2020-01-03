package com.lv.adminsys.modules.vo.web.user;

import lombok.Data;

/**
 * @author Qiang
 * @version 1.0
 * @description TODO
 * @date 2019/12/28 下午4:04
 **/
@Data
public class StudentMessageVO {


    /**
     *  用户邮箱
     */
    private String lvEmail;

    /**
     *  用户邮箱
     */
    private Integer lvQq;

    /**
     *  用户手机号
     */
    private String lvUserPhone;

    /**
     *  用户学号
     */
    private String lvUserNum;

}
