package com.tcxx.serve.wechat.model.message.event;

import com.tcxx.serve.wechat.model.message.InputBaseMessage;
import lombok.Data;

@Data
public class SubscribeEventMessage extends InputBaseMessage {

    private String event;
}
