package com.lv.adminsys.modules.vo.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @author Qiang
 * @version 1.0
 * @description TODO
 * @date 2019/12/25 下午5:17
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebLeaveListVO implements Serializable {

    private static final long serialVersionUID = 8586000044466904287L;
    /**
     *  请假状态
     */
    private String lvStatus;

    /**
     *  截止时间
     */
    private String createTime;

    /**
     *  请假id
     */
    private String lvId;

    /**
     *  标记是否已读  0 --  未读   1 --  已读
     */
    private Integer lvBook;

    /**
     *  请假人名称
     */
    private String userName;

    /**
     *  任务id
     */
    String taskId;

}
