package com.lv.adminsys.modules.vo.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author Qiang
 * @version 1.0
 * @description web端请假列表vo
 * @date 2019/12/25 下午1:39
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebTeacherCheckDTO implements Serializable {

    private static final long serialVersionUID = 798995725409776065L;

    /**
     *  账号
     */
    private String account;

    /**
     *  0 -- 历史的   1 -- 未审核的请假信息
     */
    private Integer type;

    /**
     *  学院
     */
    private Integer college;

    /**
     *  班级
     */
    private String className;

    /**
     *  页面大小
     */
    private Integer pageSize;

    /**
     *  当前页面
     */
    private Integer pageNum;

    public boolean createValidate(){
        return StringUtils.isNotEmpty(account) && pageNum != null && pageSize != null && type != null;
    }

}
