package com.lv.adminsys.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: qiang
 * @ClassName: JwtConfig
 * @Description: JWT算法配置
 * @Date: 2019/11/6 下午9:27
 * @Version: 1.0
 **/
@Data
@Component
@ConfigurationProperties("jwt.config")
public class JwtUtil {

    private String key;

    private long ttl; // 一个小时

    /**
     *  创建token令牌
     * @param id 用户ID
     * @param subject 用户名称
     * @param roles 用户角色
     * @return
     */
    public String createJwt(String id, String subject, String roles){
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key)
                .claim("role", roles);
        if(ttl > 0){
            jwtBuilder.setExpiration(new Date(nowMillis + ttl));
        }
        return jwtBuilder.compact();
    }


    /**
     * 解密JWT算法
     */
    public Claims parseJwt(String str){
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(str)
                .getBody();
    }

}
