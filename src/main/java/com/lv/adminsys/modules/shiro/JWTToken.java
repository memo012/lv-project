package com.lv.adminsys.modules.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author: qiang
 * @ClassName: JWTToken
 * @Description: TODO
 * @Date: 2019/11/20 下午4:40
 * @Version: 1.0
 **/
public class JWTToken implements AuthenticationToken {

    // 秘钥
    private String token;

    public JWTToken(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
