package com.tcxx.serve.wechat.model.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
public abstract class OutputBaseMessage {

    /**
     * 接收方帐号（收到的OpenID）
     */
    @XStreamAlias("ToUserName")
    private String toUserName;
    /**
     * 开发者微信号
     */
    @XStreamAlias("FromUserName")
    private String fromUserName;
    /**
     * 消息创建时间 （整型）
     */
    @XStreamAlias("CreateTime")
    private Long createTime;
    /**
     * 消息类型
     */
    @XStreamAlias("MsgType")
    private String msgType;
}
