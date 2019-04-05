package com.tcxx.serve.service.handler;

import com.tcxx.serve.core.utils.XStreamUtil;
import com.tcxx.serve.wechat.enumtype.EventTypeEnum;
import com.tcxx.serve.wechat.enumtype.MsgTypeEnum;
import com.tcxx.serve.wechat.model.message.InputPublicMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultMessageHandler {

    @Autowired
    private NormalMessageHandler normalMessageHandler;

    @Autowired
    private EventMessageHandler eventMessageHandler;

    public String invoke(String inputXml) {
        XStreamUtil.getInstance().setXStreamAllowTypes(new Class[]{InputPublicMessage.class});
        XStreamUtil.getInstance().setXStreamProcessAnnotations(new Class[]{InputPublicMessage.class});
        InputPublicMessage inputPublicMessage = XStreamUtil.getInstance().xmlToBean(inputXml, InputPublicMessage.class);

        // 取得消息类型
        String msgType = inputPublicMessage.getMsgType();
        if (msgType.equals(MsgTypeEnum.TEXT.getType())){
            // 文本消息
            return normalMessageHandler.handlerTextMessage(inputPublicMessage.toTextMessage());
        }else if (msgType.equals(MsgTypeEnum.EVENT.getType())){
            //获取事件类型
            String event = inputPublicMessage.getEvent();
            if (event.equals(EventTypeEnum.SUBSCRIBE.getType())){
                //关注事件
                return eventMessageHandler.handlerSubscribeEvent(inputPublicMessage.toSubscribeEventMessage());
            }else if (event.equals(EventTypeEnum.UNSUBSCRIBE.getType())){
                //取消关注事件
                return eventMessageHandler.handlerUnSubscribeEvent(inputPublicMessage.toUnSubscribeEventMessage());
            }else if (event.equals(EventTypeEnum.CLICK.getType())){
                //点击菜单拉取消息时的事件
                return eventMessageHandler.handlerClickEvent(inputPublicMessage.toClickEventMessage());
            }
        }

        return "success";
    }
}
