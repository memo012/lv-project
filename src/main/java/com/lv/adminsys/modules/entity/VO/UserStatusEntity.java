package com.lv.adminsys.modules.entity.VO;

import com.lv.adminsys.modules.entity.LvLeaveEntity;
import lombok.Data;

/**
 * @Author: qiang
 * @ClassName: UserStatusEntity
 * @Description: TODO
 * @Date: 2019/11/9 下午3:50
 * @Version: 1.0
 **/
@Data
public class UserStatusEntity extends LvLeaveEntity {

    /**
     * 学生学号
     */
    private String lvUserNum;

    /**
     * 学生姓名
     */
    private String lvUserName;

    /**
     * 学生手机号
     */
    private String lvUserPhone;

}
