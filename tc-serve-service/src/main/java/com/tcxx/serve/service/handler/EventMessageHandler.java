package com.tcxx.serve.service.handler;

import com.tcxx.serve.core.utils.BusinessUuidGenerateUtil;
import com.tcxx.serve.service.TcPublicAccountFocusService;
import com.tcxx.serve.service.TcUserAuthorSubscribeService;
import com.tcxx.serve.service.TcUserService;
import com.tcxx.serve.service.entity.TcPublicAccountFocus;
import com.tcxx.serve.service.entity.TcUser;
import com.tcxx.serve.wechat.enumtype.ClickEventKeyEnum;
import com.tcxx.serve.wechat.enumtype.MsgTypeEnum;
import com.tcxx.serve.wechat.model.message.event.ClickEventMessage;
import com.tcxx.serve.wechat.model.message.event.SubscribeEventMessage;
import com.tcxx.serve.wechat.model.message.event.UnSubscribeEventMessage;
import com.tcxx.serve.wechat.model.message.output.TextOutputMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
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
        // 关注事件 保存至公众号关注表 不关心是否保存成功 有定时任务每天拉取全量关注用户
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

        TextOutputMessage textOutputMessage = new TextOutputMessage();
        textOutputMessage.setToUserName(msg.getFromUserName());
        textOutputMessage.setFromUserName(msg.getToUserName());
        textOutputMessage.setCreateTime(new Date().getTime());
        textOutputMessage.setMsgType(MsgTypeEnum.TEXT.getType());

        StringBuffer buffer = new StringBuffer();
        buffer.append("您好，欢迎关注推球汇！").append("\n");
        buffer.append("本公众号提供体育赛事资讯，赛事解读，详情点击首页进入。").append("\n\n");
        buffer.append("客服微信：tcxx_kf").append("\n\n");
        buffer.append("如有系统使用方面的疑问，可添加客服微信进行相关咨询，我们将竭诚为您服务。");

        textOutputMessage.setContent(buffer.toString());
        return textOutputMessage.toXML();
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

    public String handlerClickEvent(ClickEventMessage msg){
        //点击菜单拉取消息时的事件
        try {
            if (ClickEventKeyEnum.CONTACT_CUSTOMER_SERVICE.getKey().equals(msg.getEventKey())) {
                // 点击联系客服菜单
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
        } catch (Exception e) {
            log.error("EventMessageHandler#handlerClickEvent error", e);
        }
        return "success";
    }

}
