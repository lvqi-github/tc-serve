package com.tcxx.serve.core.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

@Slf4j
public class JavaWebTokenUtil {

    /**
     * tc-ball token加密秘钥
     **/
    public static final String TC_BALL_TOKEN_SECRET = "N4TaU8cUysZMnop2Y4RxdkCspB6EoqTC";
    /**
     * tc-ball token 签发者
     **/
    public static final String TC_BALL_TOKEN_ISSUER = "tc-ball";
    /**
     * tc-ball token过期时间31天 大于微信授权登陆的refreshToken
     **/
    public static final int TC_BALL_TOKEN_EXPIRE_HOURS = 31 * 24;

    //---------------------------------------------------------------------------------------------------

    /**
     * tc-admin token加密秘钥
     **/
    public static final String TC_ADMIN_TOKEN_SECRET = "0oCMlAytPbsUhcFeMldadj1ddcTdRQuo";
    /**
     * tc-admin token 签发者
     **/
    public static final String TC_ADMIN_TOKEN_ISSUER = "tc-admin";
    /**
     * tc-admin token过期时间7天
     **/
    public static final int TC_ADMIN_TOKEN_EXPIRE_HOURS = 7 * 24;

    public static String createToken(String userId, String secret, String issuer, int expire) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTCreator.Builder builder = JWT.create()
                .withIssuer(issuer) // 签发者：签名由谁生成
                .withAudience(userId) // 受众：谁接受的签名
                .withExpiresAt(DateUtils.addHours(new Date(), expire)); // 签名过期时间
//                .withExpiresAt(DateUtils.addMinutes(new Date(), 5)); // 测试使用5分钟
        return builder.sign(algorithm);
    }

    public static String getIssuer(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getIssuer();
    }

    public static String getAudience(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getAudience().get(0);
    }

    public static boolean verifyToken(String userId, String token, String secret, String issuer) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .withAudience(userId)
                .build();
        try {
            verifier.verify(token);
        }catch (Exception e){
            log.error("JavaWebTokenUtil#verifyToken error", e);
            return false;
        }
        return true;
    }

    public static boolean verifyToken(String token, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        try {
            verifier.verify(token);
        }catch (Exception e){
            log.error("JavaWebTokenUtil#verifyToken error", e);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String token = JavaWebTokenUtil.createToken("userId1", JavaWebTokenUtil.TC_BALL_TOKEN_SECRET, JavaWebTokenUtil.TC_BALL_TOKEN_ISSUER, JavaWebTokenUtil.TC_BALL_TOKEN_EXPIRE_HOURS);
        System.out.println("token=" + token);

        boolean res = JavaWebTokenUtil.verifyToken(token, JavaWebTokenUtil.TC_BALL_TOKEN_SECRET);
        System.out.println("res=" + res);

//        String issuer = JavaWebTokenUtil.getIssuer(token);
//        System.out.println("issuer=" + issuer);
//
//        String userId = JavaWebTokenUtil.getAudience(token);
//        System.out.println("userId=" + userId);
//
//        boolean res = JavaWebTokenUtil.verifyToken(userId, token, JavaWebTokenUtil.TC_BALL_TOKEN_SECRET, issuer);
//        System.out.println("res=" + res);
    }

}
