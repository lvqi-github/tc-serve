package com.tcxx.serve.core.utils;

import com.tcxx.serve.core.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LockUtil {

    @Autowired
    private RedisUtil redisUtil;

    /**
     *
     * @param key
     * @param value
     * @param expireTime 过期时间
     * @return
     */
    public boolean lock(String key, String value, long expireTime) {
        try {
            return redisUtil.setNX(key, value, expireTime);
        }catch (Exception e){
            log.error("LockUtil#lock error:", e);
        }
        return false;
    }

}
