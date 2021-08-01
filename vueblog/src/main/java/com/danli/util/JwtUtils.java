package com.danli.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "fanli.jwt")
public class JwtUtils {

    private String secret;
    private long expire;
    private String header;

    /**
     * 生成 JWT Token 字符串
     *
     * @param userId       签发人id
     * expireDate       过期时间 签发时间
     * claims           额外添加到荷部分的信息。
     *                  例如可以添加用户名、用户ID、用户（加密前的）密码等信息
     */
    public String generateToken(long userId,String username) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        Map<String, Object> claims = new HashMap<String, Object>();//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        claims.put("userId", userId+"");
        claims.put("username", username);
        return Jwts.builder()    // 创建 JWT 对象
                .setHeaderParam("typ", "JWT")  //设置头部信息
                .setClaims(claims)     // 设置私有声明
                .setIssuedAt(nowDate)        //设置payload的签发时间
                .setExpiration(expireDate)   //设置payload的过期时间
                .signWith(SignatureAlgorithm.HS512, secret)  // 设置安全密钥（生成签名所需的密钥和算法）
                .compact();            //生成token（1.编码 Header 和 Payload 2.生成签名 3.拼接字符串）
    }
    /**
     *  解析token
     * JWT Token 由 头部 荷载部 和 签名部 三部分组成。签名部分是由加密算法生成，无法反向解密。
     * 而 头部 和 荷载部分是由 Base64 编码算法生成，是可以反向反编码回原样的。
     * 这也是为什么不要在 JWT Token 中放敏感数据的原因。
     *
     * @param token 加密后的token
     * @return claims 返回荷载部分的键值对
     */
    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()           // 创建解析对象
                    .setSigningKey(secret)   // 设置安全密钥（生成签名所需的密钥和算法）
                    .parseClaimsJws(token)  // 解析token
                    .getBody();     // 获取 payload 部分内容
        } catch (Exception e) {
            log.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
