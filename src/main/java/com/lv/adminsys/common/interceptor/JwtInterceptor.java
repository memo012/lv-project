package com.lv.adminsys.common.interceptor;

import com.lv.adminsys.common.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: qiang
 * @ClassName: JwtInterceptor
 * @Description: 登录拦截器
 * @Date: 2019/11/7 下午6:42
 * @Version: 1.0
 **/
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private JwtUtil jwtUtil;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经历了拦截器");
        // 无论如何都放行.具体能不能操作还是在具体的操作中去判断
        // 拦截器只是负责把头请求中包含token的令牌进行一个解析验证
        String header = request.getHeader("token");
        if(header != null && !"".equals(header)){
            // 如果有包含有token头信息,就对其进行解析
            if(header.startsWith("Bearer ")){
                // 得到token
                String token = header.substring(7);
                // 对令牌进行验证
                try{
                    Claims claims = jwtUtil.parseJwt(token);
                    String role = (String) claims.get("role");
                    if(role != null && role.equals("admin")) {
                        request.setAttribute("claims_admin", token);
                    }
                    if(role != null && role.equals("user")) {
                        request.setAttribute("claims_user", token);
                    }
                    if(role != null && role.equals("teacher")) {
                        request.setAttribute("claims_teacher", token);
                    }
                    if(role != null && role.equals("secretary")) {
                        request.setAttribute("claims_secretary", token);
                    }
                    if(role != null && role.equals("dean")) {
                        request.setAttribute("claims_dean", token);
                    }
                }catch (Exception e) {
                    throw new RuntimeException("令牌不正确!");
                }
            }
        }
        return true;
    }

}
