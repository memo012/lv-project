package com.lv.adminsys.modules.vo.login;

import com.lv.adminsys.common.utils.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @Author: qiang
 * @ClassName: UserLoginRequest
 * @Description: 学生登录请求
 * @Date: 2019/11/16 下午7:07
 * @Version: 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 1465607591192421329L;

    /**
     * 用户名
     */
    private String lvUserNum;

    /**
     * 密码
     */
    private String lvUserPassword;


    public boolean createValidate(){
        return StringUtils.isNotEmpty(lvUserNum) && StringUtils.isNotEmpty(lvUserPassword);
    }

}
