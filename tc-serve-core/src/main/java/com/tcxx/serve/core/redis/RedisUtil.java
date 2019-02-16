package com.tcxx.serve.core.redis;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public final class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        Preconditions.checkNotNull(key, "The key cannot be blank");
        Preconditions.checkArgument(time > 0, "Time must be greater than zero");
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        Preconditions.checkNotNull(key, "The key cannot be blank");
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        Preconditions.checkNotNull(key, "The key cannot be blank");
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     * @param key 键
     */
    public boolean delete(String key) {
        Preconditions.checkNotNull(key, "The key cannot be blank");
        return redisTemplate.delete(key);
    }

    // ============================String=============================

    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        Preconditions.checkNotNull(key, "The key cannot be blank");
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public void set(String key, Object value) {
        Preconditions.checkNotNull(key, "The key cannot be blank");
        Preconditions.checkNotNull(value, "The value cannot be null");
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return true成功 false 失败
     */
    public void set(String key, Object value, long time) {
        Preconditions.checkNotNull(key, "The key cannot be blank");
        Preconditions.checkNotNull(value, "The value cannot be null");
        Preconditions.checkArgument(time > 0, "Time must be greater than zero");
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 递增
     * @param key 键
     * @param delta 要增加几
     * @return
     */
    public long incr(String key, long delta) {
        Preconditions.checkNotNull(key, "The key cannot be blank");
        Preconditions.checkArgument(delta > 0, "Delta must be greater than zero");
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     * @param key 键
     * @param delta 要减少几
     * @return
     */
    public long decr(String key, long delta) {
        Preconditions.checkNotNull(key, "The key cannot be blank");
        Preconditions.checkArgument(delta > 0, "Delta must be greater than zero");
        return redisTemplate.opsForValue().increment(key, -delta);
    }

}
