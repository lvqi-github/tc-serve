package com.tcxx.serve.service.job;

import com.tcxx.serve.core.redis.RedisKeyPrefix;
import com.tcxx.serve.core.redis.RedisUtil;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.model.base.BaseAccessToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("refreshWeChatBaseAccessTokenJob")
public class RefreshWeChatBaseAccessTokenJob {

    @Autowired
    private WeChatClient weChatClient;

    @Autowired
    private RedisUtil redisUtil;

    public void execute() {
        try {
            BaseAccessToken baseAccessToken = weChatClient.base().getBaseAccessToken();
            redisUtil.set(RedisKeyPrefix.getTcAdminRedisKey(RedisKeyPrefix.TC_ADMIN_WECHAT_BASE_ACCESS_TOKEN, "key"), baseAccessToken.getAccessToken(), 115 * 60); //115分钟
        }catch (Exception e){
            log.error("RefreshWeChatBaseAccessTokenJob#execute error", e);
        }
    }

}
