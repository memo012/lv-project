package com.lv.adminsys.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author: qiang
 * @ClassName: WebSecurityConfig
 * @Description: 安全配置类
 * @Date: 2019/11/6 下午7:14
 * @Version: 1.0
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         *  authorizeRequests(): 所有security全注解配置实现的开端,表示开始说明需要的权限.
         *  需要的权限分两部分,第一部分是拦截的路径,第二部分访问该路径需要的权限.
         *  antMatchers("/**"): 表示拦截的路径
         *  permitAll(): 表示任何权限都可以访问,直接放行所有.
         *  anyRequest(): 表示任何请求
         *  authenticated(): 表示认证后才能访问
         *  and().csrf().disable(): 固定写法,表示使csrf拦截失效.
         */
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
