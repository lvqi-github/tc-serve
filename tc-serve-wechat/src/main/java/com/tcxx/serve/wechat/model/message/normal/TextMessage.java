package com.tcxx.serve.wechat.model.message.normal;

import com.tcxx.serve.wechat.model.message.InputBaseMessage;
import lombok.Data;

@Data
public class TextMessage extends InputBaseMessage {

    //文本消息内容
    private String Content;

}
