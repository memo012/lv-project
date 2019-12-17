package com.lv.adminsys.modules.vo.leave;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Qiang
 * @version 1.0
 * @description TODO
 * @date 2019/12/8 下午3:20
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherCheckHisResponse implements Serializable {

    private static final long serialVersionUID = -7059416934485819562L;

    /**
     *  请假人姓名
     */
    private String userName;

    /**
     *  请假人学号
     */
    private String userNum;

    /**
     * 请假开始时间
     */
    private String lvBeginTime;

    /**
     * 请假截止时间
     */
    private String lvEndTime;

    /**
     *  请假次数
     */
    private Integer lvNum;


}
