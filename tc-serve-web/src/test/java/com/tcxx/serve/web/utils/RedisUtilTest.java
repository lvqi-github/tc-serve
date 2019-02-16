package com.tcxx.serve.web.utils;

import com.tcxx.serve.core.redis.RedisUtil;
import com.tcxx.serve.web.TcServeWebApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisUtilTest extends TcServeWebApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 设置缓存
     */
    @Test
    public void testSet() {
        redisUtil.set("test-1", "{\"street\":\"科技园路\",\"city\":\"江苏苏州\",\"country\":\"中国\"}");
    }

    /**
     * 是否存在key
     */
    @Test
    public void testHasKey() {
        boolean res = redisUtil.hasKey("test-1");
        System.out.println("res=" + res);
    }

    /**
     * 获取缓存
     */
    @Test
    public void testGet() {
        Object object = redisUtil.get("test-1");
        System.out.println("object=" + object.toString());
    }

    /**
     * 设置缓存有效期
     */
    @Test
    public void testExpire() {
        boolean res = redisUtil.expire("test-1", 60 * 60);
        System.out.println("res=" + res);
    }

    /**
     * 获取缓存有效期
     */
    @Test
    public void testGetExpire() {
        long expire = redisUtil.getExpire("test-1");
        System.out.println("expire=" + expire);
    }

    /**
     * 设置缓存并设置有效期
     */
    @Test
    public void testSetAndExpire() {
        redisUtil.set("test-1", "abcdefg", 60 * 60);
    }

    /**
     * 删除缓存
     */
    @Test
    public void testDelete() {
        boolean res = redisUtil.delete("test-1");
        System.out.println("res=" + res);
    }

}
