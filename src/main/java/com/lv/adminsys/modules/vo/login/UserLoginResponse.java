package com.lv.adminsys.modules.vo.login;

import com.lv.adminsys.common.utils.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: qiang
 * @ClassName: UserLoginResponse
 * @Description: 登录响应
 * @Date: 2019/11/16 下午7:03
 * @Version: 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse implements Serializable {
    private static final long serialVersionUID = -8754850929762230640L;

    /**
     *  唯一标识符
     */
    private String id;

    /**
     * 学号
     */
    private String lvUserNum;

    /**
     *  用户名
     */
    private String lvUserName;

    /**
     *  联系方式
     */
    private String lvUserPhone;

    /**
     * 令牌
     */
    private String token;

    /**
     *  用户角色
     */
    private Integer roleNum;

    /**
     *  所属学院
     */
    private Integer collage;


    public UserLoginResponse(String lvUserNum, String lvUserName, String lvUserPhone, String token, Integer roleNum, Integer collage){
        this.id = new TimeUtil().getLongTime();
        this.lvUserNum = lvUserNum;
        this.lvUserName = lvUserName;
        this.lvUserPhone = lvUserPhone;
        this.token = token;
        this.roleNum = roleNum;
        this.collage = collage;
    }

}
