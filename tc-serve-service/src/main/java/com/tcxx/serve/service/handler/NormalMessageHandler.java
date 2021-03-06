package com.tcxx.serve.service.handler;

import com.tcxx.serve.wechat.enumtype.MsgTypeEnum;
import com.tcxx.serve.wechat.model.message.normal.TextMessage;
import com.tcxx.serve.wechat.model.message.output.TextOutputMessage;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class NormalMessageHandler {

    public String handlerTextMessage(TextMessage msg) {
        TextOutputMessage textOutputMessage = new TextOutputMessage();
        textOutputMessage.setToUserName(msg.getFromUserName());
        textOutputMessage.setFromUserName(msg.getToUserName());
        textOutputMessage.setCreateTime(new Date().getTime());
        textOutputMessage.setMsgType(MsgTypeEnum.TEXT.getType());

        StringBuffer buffer = new StringBuffer();
        buffer.append("尊敬的用户，您好").append("\n\n");
        buffer.append("客服微信：tcxx_kf").append("\n\n");
        buffer.append("如有系统使用方面的疑问，可添加客服微信进行相关咨询，我们将竭诚为您服务。");

        textOutputMessage.setContent(buffer.toString());
        return textOutputMessage.toXML();
    }
}
