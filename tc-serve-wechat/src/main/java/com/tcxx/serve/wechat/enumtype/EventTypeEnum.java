package com.tcxx.serve.wechat.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventTypeEnum {

    /**
     * 订阅
     */
    SUBSCRIBE("subscribe"),
    /**
     * 取消订阅
     */
    UNSUBSCRIBE("unsubscribe"),
    /**
     * 点击菜单拉取消息时的事件
     */
    CLICK("CLICK"),
    /**
     * 点击菜单跳转链接时的事件
     */
    VIEW("VIEW"),
    /**
     * 模板消息发送结果推送事件
     */
    TEMPLATE_SEND_JOB_FINISH("TEMPLATESENDJOBFINISH")
    ;

    private String type;

}
