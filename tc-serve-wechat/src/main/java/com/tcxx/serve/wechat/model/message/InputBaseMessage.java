package com.tcxx.serve.wechat.model.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public abstract class InputBaseMessage {

    /**
     * 开发者微信号
     */
    @XStreamAlias("ToUserName")
    private String toUserName;
    /**
     * 发送方帐号（一个OpenID）
     */
    @XStreamAlias("FromUserName")
    private String fromUserName;
    /**
     * 消息创建时间 （整型）
     */
    @XStreamAlias("CreateTime")
    private Long createTime;
    /**
     * 消息id，64位整型
     */
    @XStreamAlias("MsgId")
    private Long msgId;
    /**
     * 消息类型
     */
    @XStreamAlias("MsgType")
    private String msgType;

}
