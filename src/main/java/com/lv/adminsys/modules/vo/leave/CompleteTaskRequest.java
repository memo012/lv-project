package com.lv.adminsys.modules.vo.leave;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author Qiang
 * @version 1.0
 * @description 审核请求类
 * @date 2019/12/7 下午8:21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteTaskRequest implements Serializable {
    private static final long serialVersionUID = -8309768120151671063L;


    /**
     *  任务id
     */
    String taskId;

    /**
     *  审核人的工号
     */
    String userId;

    /**
     *  审核意见
     */
    String aduit;

    /**
     *  审核具体原因
     */
    String comment;

    /**
     *  请假条id
     */
    String lvId;

    public boolean createValidate(){
        return StringUtils.isNotEmpty(taskId) && StringUtils.isNotEmpty(userId)
                && StringUtils.isNotEmpty(aduit) && StringUtils.isNotEmpty(lvId);
    }

}
