package com.tcxx.serve.wechat.model.message.event;

import com.tcxx.serve.wechat.model.message.InputBaseMessage;
import lombok.Data;

@Data
public class ClickEventMessage extends InputBaseMessage {

    private String event;

    /**
     * 事件KEY值，与自定义菜单接口中KEY值对应
     */
    private String eventKey;
}
