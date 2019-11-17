package com.lv.adminsys.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Author: qiang
 * @ClassName: SecurityConfig
 * @Description: TODO
 * @Date: 2019/11/16 下午7:19
 * @Version: 1.0
 **/
@Component
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

}
