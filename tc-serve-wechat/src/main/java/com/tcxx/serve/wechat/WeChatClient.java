package com.tcxx.serve.wechat;

import com.tcxx.serve.wechat.component.AbstractComponent;
import com.tcxx.serve.wechat.component.BaseComponent;
import com.tcxx.serve.wechat.component.MenuComponent;
import com.tcxx.serve.wechat.component.SnsComponent;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WeChatClient {

    private WeChatConfiguration weChatConfiguration;

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
    public WeChatClient(WeChatConfiguration weChatConfiguration) {
        this.weChatConfiguration = weChatConfiguration;
        this.appId = weChatConfiguration.getAppId();
        this.appSecret = weChatConfiguration.getAppSecret();
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

}
