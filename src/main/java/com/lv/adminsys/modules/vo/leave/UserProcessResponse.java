package com.lv.adminsys.modules.vo.leave;

import com.lv.adminsys.common.utils.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: qiang
 * @ClassName: UserApplyListResponse
 * @Description: 请假进度详情
 * @Date: 2019/11/15 下午9:26
 * @Version: 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProcessResponse implements Serializable {

    private static final long serialVersionUID = -541501377726179479L;

    /**
     * 唯一标识符
     */
    private String id;

    /**
     * 老师名称
     */
    private String teacherName;

    /**
     * 审批时间
     */
    private String checkTime;

    /**
     * 老师办公室地址
     */
    private String lvTeacherLocation;

    /**
     * 老师工作时间
     */
    private String lvTeacherWorkTime;

    /**
     * 审批内容
     */
    private String taskComment;

    /**
     * 审批结果
     */
    private String status;

    public UserProcessResponse(String teacherName, String checkTime, String lvTeacherLocation, String lvTeacherWorkTime, String taskComment, String status){
        this.id = new TimeUtil().getLongTime();
        this.teacherName = teacherName;
        this.checkTime = checkTime;
        this.lvTeacherLocation = lvTeacherLocation;
        this.lvTeacherWorkTime = lvTeacherWorkTime;
        this.taskComment = taskComment;
        this.status = status;
    }


}
