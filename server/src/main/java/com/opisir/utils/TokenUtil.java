package com.opisir.utils;

import com.opisir.config.AppProperties;
import com.opisir.model.res.LoginResp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther: dingjn
 * @Desc: jwt工具类
 */
@Component
public class TokenUtil {

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    private static AppProperties appProperties;

    @Autowired
    public void setAppProperties(AppProperties appProperties) {
        TokenUtil.appProperties = appProperties;
    }

    /**
     * 生成token.
     */
    private static String createToken(UserDetails userDetails, Map<String, Object> claims, long expired) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + expired * 1000))
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SIGNATURE_ALGORITHM, appProperties.getJwt().getSecret())
                .compact();
    }

    private static String createAccessToken(UserDetails userDetails, Map<String, Object> claims) {
        return createToken(userDetails, claims, appProperties.getJwt().getExpired());
    }

    private static String createRefreshToken(UserDetails userDetails, Map<String, Object> claims) {
        return createToken(userDetails, claims, appProperties.getJwt().getExpired());
    }

    public LoginResp generatedToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String accessToken = createAccessToken(userDetails, claims);
        String refreshToken = createRefreshToken(userDetails, claims);
        return LoginResp.builder().accessToken(appProperties.getJwt().getPrefix() + accessToken).refreshToken(appProperties.getJwt().getPrefix() + refreshToken).build();
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public boolean isTokenNotExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 解密jwt，获取实体
     *
     * @param jwt
     */
    private Claims getClaimsFromToken(String jwt) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(appProperties.getJwt().getSecret()).parseClaimsJws(jwt).getBody();
        } catch (Exception ex) {
            claims = null;
        }
        return claims;
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && isTokenNotExpired(token);
    }
}
