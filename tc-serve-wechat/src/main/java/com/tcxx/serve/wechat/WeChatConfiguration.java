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
}
