package com.tcxx.serve.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wechat.pay", ignoreUnknownFields = false)
@PropertySource(value = "classpath:wechat.properties")
public class WeChatPayConfiguration {

    /**
     * 微信支付_商户ID
     */
    private String merchantId;

    /**
     * 微信支付_API秘钥
     */
    private String apiSecretKey;

    /**
     * 微信支付_异步接收微信支付结果通知的回调地址
     */
    private String notifyUrl;
}
