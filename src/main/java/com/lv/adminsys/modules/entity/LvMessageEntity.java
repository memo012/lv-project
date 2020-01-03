package com.lv.adminsys.modules.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Qiang
 * @version 1.0
 * @description TODO
 * @date 2019/12/26 下午12:39
 **/
@Data
@TableName(value = "lv_message")
@AllArgsConstructor
@NoArgsConstructor
public class LvMessageEntity implements Serializable {
    private static final long serialVersionUID = 4122084536371773597L;

    /**
     * 请假消息 id
     */
    @TableId("lv_message_id")
    private String lvMessageId;

    /**
     *  审核任务id
     */
    private String lvTaskId;

    /**
     *  请假id
     */
    private String lvId;

    /**
     *  标记是否已读
     *  0 --  未读 1 -- 已读
     *  -1 -- 删除
     */
    private Integer lvBook;

    /**
     *  审核人工号
     */
    private String lvTeacherNum;


    /**
     *  请假人姓名
     */
    private String lvUserName;

    /**
     *  请假人请假时间
     */
    private String lvUserCreateTime;

}
