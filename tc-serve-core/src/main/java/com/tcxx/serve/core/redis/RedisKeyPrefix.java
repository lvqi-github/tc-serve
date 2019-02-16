package com.tcxx.serve.core.redis;

public class RedisKeyPrefix {

    private static final String TC_BALL_PREFIX = "tc_ball";

    public static final String TC_BALL_WECHAT_AUTH_ACCESS_TOKEN = "wechat_auth_access_token";

    public static final String TC_BALL_WECHAT_AUTH_REFRESH_TOKEN = "wechat_auth_refresh_token";

    //-------------------------------------------------------------

    private static final String TC_ADMIN_PREFIX = "tc_admin";

    public static String getTcBallRedisKey(String prefix, String value) {
        return String.format("%s_%s_%s", TC_BALL_PREFIX, prefix, value);
    }

    public static String getTcAdminRedisKey(String prefix, String value) {
        return String.format("%s_%s_%s", TC_ADMIN_PREFIX, prefix, value);
    }

}
