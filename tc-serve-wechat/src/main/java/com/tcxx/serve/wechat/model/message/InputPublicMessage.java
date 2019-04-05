package com.tcxx.serve.wechat.model.message;

import com.tcxx.serve.wechat.model.message.event.ClickEventMessage;
import com.tcxx.serve.wechat.model.message.event.SubscribeEventMessage;
import com.tcxx.serve.wechat.model.message.event.UnSubscribeEventMessage;
import com.tcxx.serve.wechat.model.message.event.ViewEventMessage;
import com.tcxx.serve.wechat.model.message.normal.TextMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@XStreamAlias("xml")
public class InputPublicMessage extends InputBaseMessage {

    // 文本消息
    @XStreamAlias("Content")
    private String content;

    // 事件
    @XStreamAlias("Event")
    private String event;

    @XStreamAlias("EventKey")
    private String eventKey;

    public TextMessage toTextMessage() {
        TextMessage inputMessage = new TextMessage();
        inputMessage.setContent(content);
        initMessage(inputMessage);
        return inputMessage;
    }

    public SubscribeEventMessage toSubscribeEventMessage() {
        SubscribeEventMessage inputMessage = new SubscribeEventMessage();
        inputMessage.setEvent(event);
        initMessage(inputMessage);
        return inputMessage;
    }

    public UnSubscribeEventMessage toUnSubscribeEventMessage() {
        UnSubscribeEventMessage inputMessage = new UnSubscribeEventMessage();
        inputMessage.setEvent(event);
        initMessage(inputMessage);
        return inputMessage;
    }

    public ClickEventMessage toClickEventMessage() {
        ClickEventMessage inputMessage = new ClickEventMessage();
        inputMessage.setEvent(event);
        inputMessage.setEventKey(eventKey);
        initMessage(inputMessage);
        return inputMessage;
    }

    public ViewEventMessage toViewEventMessage() {
        ViewEventMessage inputMessage = new ViewEventMessage();
        inputMessage.setEvent(event);
        inputMessage.setEventKey(eventKey);
        initMessage(inputMessage);
        return inputMessage;
    }

    private void initMessage(InputBaseMessage inputBaseMessage) {
        inputBaseMessage.setToUserName(this.getToUserName());
        inputBaseMessage.setFromUserName(this.getFromUserName());
        inputBaseMessage.setCreateTime(this.getCreateTime());
        inputBaseMessage.setMsgId(this.getMsgId());
        inputBaseMessage.setMsgType(this.getMsgType());
    }
}
