package com.tcxx.serve.service.job;

import com.alibaba.fastjson.JSON;
import com.tcxx.serve.core.exception.WeChatInvokeRuntimeException;
import com.tcxx.serve.service.TcTemplateMessagePushService;
import com.tcxx.serve.service.entity.TcTemplateMessagePush;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.model.message.template.TemplateMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("pushTemplateMessageJob")
public class PushTemplateMessageJob {

    @Autowired
    private WeChatClient weChatClient;

    @Autowired
    private TcTemplateMessagePushService tcTemplateMessagePushService;

    public void execute() {
        try {
            //获取需要推送的模板消息 每次job执行获取200条
            int pageSize = 200;
            List<TcTemplateMessagePush> templateMessagePushList = tcTemplateMessagePushService.listNotPushTemplateMessage(pageSize);
            for (TcTemplateMessagePush tcTemplateMessagePush : templateMessagePushList) {
                try {
                    TemplateMessage templateMessage = JSON.parseObject(tcTemplateMessagePush.getDataContent(), TemplateMessage.class);
                    String msgId = weChatClient.message().sendTemplateMessage(templateMessage);
                    tcTemplateMessagePushService.updatePushStatusSuccess(tcTemplateMessagePush.getPushId(), msgId);
                    Thread.sleep(300); //休眠300毫秒
                } catch (WeChatInvokeRuntimeException e){
                    log.error("PushTemplateMessageJob#execute WeChatInvokeRuntimeException error", e);
                    tcTemplateMessagePushService.updatePushStatusFailed(tcTemplateMessagePush.getPushId());
                } catch (Exception e){
                    log.error("PushTemplateMessageJob#execute sendTemplateMessage error", e);
                }
            }
        }catch (Exception e){
            log.error("PushTemplateMessageJob#execute error", e);
        }
    }

}
