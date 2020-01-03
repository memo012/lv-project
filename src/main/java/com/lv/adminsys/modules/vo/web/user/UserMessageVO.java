package com.lv.adminsys.modules.vo.web.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Qiang
 * @version 1.0
 * @description TODO
 * @date 2019/12/28 下午4:25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMessageVO {

    /**
     *  用户邮箱
     */
    private String lvEmail;

    /**
     *  用户qq
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

    public boolean isCreate(){
        return StringUtils.isNotEmpty(lvUserNum);
    }

}
