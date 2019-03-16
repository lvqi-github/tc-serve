package com.tcxx.serve.web.wechat;

import com.alibaba.fastjson.JSON;
import com.tcxx.serve.web.TcServeWebApplicationTests;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.model.message.template.TemplateData;
import com.tcxx.serve.wechat.model.message.template.TemplateMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class MessageComponentTest extends TcServeWebApplicationTests {

    @Autowired
    private WeChatClient weChatClient;

    @Test
    public void testSendTemplateMessage() {

        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setToUser("oV7La5qscLj4arp5Rq5MyGkMYelk");
        templateMessage.setTemplateId("SAowgybTTTarsqWlwSp1v3FjfxMC24C8h4f0TPA2PCM");
        templateMessage.setUrl("www.baidu.com");

        Map<String, TemplateData> data = new HashMap<>();

        TemplateData firstData = new TemplateData();
        firstData.setValue("您好，您关注的作者「飞刀」发布了新的文章。");
        firstData.setColor("#FFA500");
        data.put("first", firstData);

        TemplateData titleData = new TemplateData();
        titleData.setValue("飞刀 2019-03-11 夜间足球5场");
        titleData.setColor("#FF0000");
        data.put("title", titleData);

        TemplateData releaseTimeData = new TemplateData();
        releaseTimeData.setValue("2019-03-11 21:46:57");
        releaseTimeData.setColor("#0000FF");
        data.put("releaseTime", releaseTimeData);

        TemplateData remarkData = new TemplateData();
        remarkData.setValue("点击查看详情");
        remarkData.setColor("#0000FF");
        data.put("remark", remarkData);

        templateMessage.setData(data);
        System.out.println(JSON.toJSON(templateMessage).toString());

        String msgId = weChatClient.message().sendTemplateMessage(templateMessage);
        System.out.println("msgId=" + msgId);
    }
}
