package com.tcxx.serve.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wechat", ignoreUnknownFields = false)
@PropertySource(value = "classpath:wechat.properties")
public class WeChatConfiguration {

    /**
     * appId
     */
    private String appId;
    /**
     * appSecret
     */
    private String appSecret;
    /**
     * 用于微信接入服务器有效性验证及消息事件验证
     */
    private String accessToken;
    /**
     * 公众号微信id
     */
    private String publicAccountWechatId;
}
