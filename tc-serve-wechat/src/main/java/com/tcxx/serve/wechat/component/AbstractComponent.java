package com.tcxx.serve.wechat.component;


import com.tcxx.serve.wechat.WeChatClient;

public abstract class AbstractComponent {

    protected WeChatClient weChatClient;

    public AbstractComponent(WeChatClient weChatClient) {
        if (weChatClient == null) {
            throw new IllegalArgumentException("WeChatClient can not be null");
        }
        this.weChatClient = weChatClient;
    }
}
