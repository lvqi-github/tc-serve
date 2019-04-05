package com.tcxx.serve.wechat.model.message.event;

import com.tcxx.serve.wechat.model.message.InputBaseMessage;
import lombok.Data;

@Data
public class ViewEventMessage extends InputBaseMessage {

    private String event;

    /**
     * 事件KEY值，设置的跳转URL
     */
    private String eventKey;

}
