package com.tcxx.serve.service.handler;

import com.tcxx.serve.wechat.model.message.normal.TextMessage;
import org.springframework.stereotype.Component;

@Component
public class NormalMessageHandler {

    public String handlerTextMessage(TextMessage msg) {
        return "success";
    }
}
