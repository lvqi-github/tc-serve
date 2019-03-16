package com.tcxx.serve.web.service.handler;

import com.tcxx.serve.service.handler.DefaultMessageHandler;
import com.tcxx.serve.web.TcServeWebApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultMessageHandlerTest extends TcServeWebApplicationTests {

    @Autowired
    private DefaultMessageHandler defaultMessageHandler;

    @Test
    public void testInvoke() {
        String xml = "<xml>\n" +
                "  <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[FromUser]]></FromUserName>\n" +
                "  <CreateTime>123456789</CreateTime>\n" +
                "  <MsgType><![CDATA[event]]></MsgType>\n" +
                "  <Event><![CDATA[subscribe]]></Event>\n" +
                "</xml>";

        String invoke = defaultMessageHandler.invoke(xml);
        System.out.println(invoke);
    }
}
