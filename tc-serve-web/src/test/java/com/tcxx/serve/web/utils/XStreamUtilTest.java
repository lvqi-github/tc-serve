package com.tcxx.serve.web.utils;

import com.tcxx.serve.core.utils.XStreamUtil;
import com.tcxx.serve.web.TcServeWebApplicationTests;
import com.tcxx.serve.wechat.enumtype.MsgTypeEnum;
import com.tcxx.serve.wechat.model.message.output.TextOutputMessage;
import org.junit.Test;

import java.util.Date;

public class XStreamUtilTest extends TcServeWebApplicationTests {

    @Test
    public void testBeanToXml() {

        TextOutputMessage textOutputMessage = new TextOutputMessage();
        textOutputMessage.setToUserName("ToUserName");
        textOutputMessage.setFromUserName("FromUserName");
        textOutputMessage.setCreateTime(new Date().getTime());
        textOutputMessage.setMsgType(MsgTypeEnum.TEXT.getType());

        StringBuffer buffer = new StringBuffer();
        buffer.append("尊敬的用户，您好").append("\n\n");
        buffer.append("客服微信：tcxx_kf").append("\n\n");
        buffer.append("如有系统使用方面的疑问，可添加客服微信进行相关咨询，我们将竭诚为您服务。");

        textOutputMessage.setContent(buffer.toString());
        System.out.println(XStreamUtil.getInstance().beanToXml(textOutputMessage));
    }
}
