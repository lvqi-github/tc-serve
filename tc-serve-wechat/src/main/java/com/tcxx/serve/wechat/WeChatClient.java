package com.tcxx.serve.wechat;

import com.tcxx.serve.core.redis.RedisKeyPrefix;
import com.tcxx.serve.core.redis.RedisUtil;
import com.tcxx.serve.wechat.component.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WeChatClient {

    @Getter
    private WeChatConfiguration weChatConfiguration;

    @Getter
    private WeChatPayConfiguration weChatPayConfiguration;

    private RedisUtil redisUtil;

    /**
     * appId
     */
    @Getter
    private final String appId;

    /**
     * appSecret
     */
    @Getter
    private final String appSecret;

    @Autowired
    public WeChatClient(WeChatConfiguration weChatConfiguration, WeChatPayConfiguration weChatPayConfiguration, RedisUtil redisUtil) {
        this.weChatConfiguration = weChatConfiguration;
        this.weChatPayConfiguration = weChatPayConfiguration;
        this.redisUtil = redisUtil;
        this.appId = weChatConfiguration.getAppId();
        this.appSecret = weChatConfiguration.getAppSecret();
    }

    public String getBaseAccessToken() {
        return redisUtil.get(RedisKeyPrefix.getTcAdminRedisKey(RedisKeyPrefix.TC_ADMIN_WECHAT_BASE_ACCESS_TOKEN, "key")).toString();
    }

    /**
     * 新增组件
     */
    private final Map<String, AbstractComponent> components = new HashMap<>();

    public SnsComponent sns() {
        String key = SnsComponent.class.getName();
        if (components.containsKey(key)) {
            return (SnsComponent) components.get(key);
        }
        SnsComponent component = new SnsComponent(this);
        components.put(key, component);
        return component;
    }

    public BaseComponent base() {
        String key = BaseComponent.class.getName();
        if (components.containsKey(key)) {
            return (BaseComponent) components.get(key);
        }
        BaseComponent component = new BaseComponent(this);
        components.put(key, component);
        return component;
    }

    public MenuComponent menu() {
        String key = MenuComponent.class.getName();
        if (components.containsKey(key)) {
            return (MenuComponent) components.get(key);
        }
        MenuComponent component = new MenuComponent(this);
        components.put(key, component);
        return component;
    }

    public MessageComponent message() {
        String key = MessageComponent.class.getName();
        if (components.containsKey(key)) {
            return (MessageComponent) components.get(key);
        }
        MessageComponent component = new MessageComponent(this);
        components.put(key, component);
        return component;
    }

    public UserComponent user() {
        String key = UserComponent.class.getName();
        if (components.containsKey(key)) {
            return (UserComponent) components.get(key);
        }
        UserComponent component = new UserComponent(this);
        components.put(key, component);
        return component;
    }

    public PayComponent pay() {
        String key = PayComponent.class.getName();
        if (components.containsKey(key)) {
            return (PayComponent) components.get(key);
        }
        PayComponent component = new PayComponent(this);
        components.put(key, component);
        return component;
    }

}
