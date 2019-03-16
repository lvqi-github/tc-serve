package com.tcxx.serve.wechat.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MsgTypeEnum {

    /**
     * 1 文本消息
     */
    TEXT("text"),
    /**
     * 2 图片消息
     */
    IMAGE("image"),
    /**
     * 3 语音消息
     */
    VOICE("voice"),
    /**
     * 4 视频消息
     */
    VIDEO("video"),
    /**
     * 5 小视频消息
     */
    SHORT_VIDEO("shortvideo"),
    /**
     * 6 地理位置消息
     */
    LOCATION("location"),
    /**
     * 7 链接消息
     */
    LINK("link"),
    /**
     * 事件消息
     */
    EVENT("event")
    ;

    private String type;

}
