package com.lv.adminsys.modules.vo.leave;

import com.lv.adminsys.common.utils.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Qiang
 * @version 1.0
 * @description 老师历史页面响应
 * @date 2019/12/17 上午10:44
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherHisItems extends TeacherCheckResponse implements Serializable {

    private static final long serialVersionUID = 5647346973409948756L;

    /**
     * 请假状态
     */
    private String status;

    public TeacherHisItems(String userName, String lvUserNum,
                           String lvBeginTime, String lvEndTime,
                           Integer lvNum, String lvId, String status) {
        setId(new TimeUtil().getLongTime());
        setUserName(userName);
        setLvUserNum(lvUserNum);
        setLvBeginTime(lvBeginTime);
        setLvEndTime(lvEndTime);
        setLvNum(lvNum);
        setLvId(lvId);
        this.status = status;
    }

}
