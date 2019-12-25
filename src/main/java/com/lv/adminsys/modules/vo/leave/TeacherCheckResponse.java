package com.lv.adminsys.modules.vo.leave;

import com.lv.adminsys.common.utils.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Qiang
 * @version 1.0
 * @description TODO
 * @date 2019/12/7 下午8:04
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherCheckResponse implements Serializable {

    private static final long serialVersionUID = -715968708206201000L;

    /**
     *  唯一标识符
     */
    private String id;

    /**
     *  请假人姓名
     */
    private String userName;

    /**
     *  请假人学号
     */
    private String lvUserNum;

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

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 请假ID
     */
    private String lvId;


    public TeacherCheckResponse(String userName, String lvUserNum, String lvBeginTime, String lvEndTime, Integer lvNum, String taskId, String lvId){
        this.id = new TimeUtil().getLongTime();
        this.userName = userName;
        this.lvUserNum = lvUserNum;
        this.lvBeginTime = lvBeginTime;
        this.lvEndTime = lvEndTime;
        this.lvNum = lvNum;
        this.taskId = taskId;
        this.lvId = lvId;
    }

}
