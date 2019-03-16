package com.tcxx.serve.service.handler;

import com.tcxx.serve.core.utils.BusinessUuidGenerateUtil;
import com.tcxx.serve.service.TcPublicAccountFocusService;
import com.tcxx.serve.service.TcUserAuthorSubscribeService;
import com.tcxx.serve.service.TcUserService;
import com.tcxx.serve.service.entity.TcPublicAccountFocus;
import com.tcxx.serve.service.entity.TcUser;
import com.tcxx.serve.wechat.model.message.event.SubscribeEventMessage;
import com.tcxx.serve.wechat.model.message.event.UnSubscribeEventMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class EventMessageHandler {

    @Autowired
    private TcPublicAccountFocusService tcPublicAccountFocusService;

    @Autowired
    private TcUserAuthorSubscribeService tcUserAuthorSubscribeService;

    @Autowired
    private TcUserService tcUserService;

    public String handlerSubscribeEvent(SubscribeEventMessage msg){
        // 关注事件 保存至公众号关注表 不关注是否保存成功 有定时任务每天拉取全量关注用户
        try {
            String uuid = BusinessUuidGenerateUtil.getTcPublicAccountFocusUuid(msg.getToUserName(), msg.getFromUserName());
            TcPublicAccountFocus publicAccountFocus = tcPublicAccountFocusService.getByUuid(uuid);
            // 表中不存在 新增
            if (Objects.isNull(publicAccountFocus)){
                publicAccountFocus = new TcPublicAccountFocus();
                publicAccountFocus.setPublicAccountWechatId(msg.getToUserName());
                publicAccountFocus.setOpenId(msg.getFromUserName());
                tcPublicAccountFocusService.insert(publicAccountFocus);
            }
        } catch (Exception e) {
            log.error("EventMessageHandler#handlerSubscribeEvent error", e);
        }

        return "success";
    }

    public String handlerUnSubscribeEvent(UnSubscribeEventMessage msg){
        //取消关注事件 删除公众号关注表数据 并清空该用户的订阅作者数据
        try {
            String uuid = BusinessUuidGenerateUtil.getTcPublicAccountFocusUuid(msg.getToUserName(), msg.getFromUserName());
            TcPublicAccountFocus publicAccountFocus = tcPublicAccountFocusService.getByUuid(uuid);
            // 表中存在 删除
            if (Objects.nonNull(publicAccountFocus)){
                tcPublicAccountFocusService.deleteByUuid(uuid);
                //获取用户信息
                TcUser user = tcUserService.getByOpenId(msg.getFromUserName());
                if (Objects.nonNull(user)){
                    //清空该用户的订阅作者数据
                    tcUserAuthorSubscribeService.deleteByUserId(user.getId());
                }
            }
        } catch (Exception e) {
            log.error("EventMessageHandler#handlerUnSubscribeEvent error", e);
        }
        return "success";
    }

}
