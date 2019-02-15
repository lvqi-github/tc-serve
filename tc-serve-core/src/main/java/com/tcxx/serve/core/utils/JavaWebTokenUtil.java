package com.tcxx.serve.core.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

public class JavaWebTokenUtil {

    /**
     * WeChatClient token加密秘钥
     **/
    public static final String WECHAT_TOKEN_SECRET = "N4TaU8cUysZMnop2Y4RxdkCspB6EoqTC";
    /**
     * WeChatClient token 签发者
     **/
    public static final String WECHAT_TOKEN_ISSUER = "tc-ball-mobile";
    /**
     * WeChatClient token过期时间1小时
     **/
    public static final int WECHAT_TOKEN_EXPIRE_HOURS = 1;

    public static String createToken(String userId, String secret, String issuer, int expire) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTCreator.Builder builder = JWT.create()
                .withIssuer(issuer) // 签发者：签名由谁生成
                .withAudience(userId) // 受众：谁接受的签名
                .withExpiresAt(DateUtils.addHours(new Date(), expire)); // 签名过期时间
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
        verifier.verify(token);
        return true;
    }

    public static void main(String[] args) {
        String token = JavaWebTokenUtil.createToken("userId1", JavaWebTokenUtil.WECHAT_TOKEN_SECRET, JavaWebTokenUtil.WECHAT_TOKEN_ISSUER, JavaWebTokenUtil.WECHAT_TOKEN_EXPIRE_HOURS);
        System.out.println("token=" + token);

        String issuer = JavaWebTokenUtil.getIssuer(token);
        System.out.println("issuer=" + issuer);

        String userId = JavaWebTokenUtil.getAudience(token);
        System.out.println("userId=" + userId);

        boolean res = JavaWebTokenUtil.verifyToken(userId, token, JavaWebTokenUtil.WECHAT_TOKEN_SECRET, issuer);
        System.out.println("res=" + res);
    }

}
