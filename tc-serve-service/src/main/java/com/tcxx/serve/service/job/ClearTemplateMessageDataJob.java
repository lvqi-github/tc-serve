package com.tcxx.serve.service.job;

import com.tcxx.serve.service.TcTemplateMessagePushService;
import com.tcxx.serve.service.entity.TcTemplateMessagePush;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("clearTemplateMessageDataJob")
public class ClearTemplateMessageDataJob {

    @Autowired
    private TcTemplateMessagePushService tcTemplateMessagePushService;

    public void execute() {
        try {
            // 获取需要清理的模板消息 每次job执行获取1000条
            int pageSize = 1000;
            List<TcTemplateMessagePush> clearTemplateMessageList = tcTemplateMessagePushService.listClearTemplateMessage(pageSize);
            for (TcTemplateMessagePush tcTemplateMessagePush : clearTemplateMessageList) {
                try {
                    tcTemplateMessagePushService.deleteByPushId(tcTemplateMessagePush.getPushId());
                }catch (Exception e) {
                    log.error("ClearTemplateMessageDataJob#execute deleteByPushId error", e);
                }
            }
        }catch (Exception e){
            log.error("ClearTemplateMessageDataJob#execute error", e);
        }
    }

}
