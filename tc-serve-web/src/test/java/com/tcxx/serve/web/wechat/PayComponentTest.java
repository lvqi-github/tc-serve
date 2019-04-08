package com.tcxx.serve.web.wechat;

import com.tcxx.serve.web.TcServeWebApplicationTests;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.util.WeChatPaySignUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PayComponentTest extends TcServeWebApplicationTests {

    @Autowired
    private WeChatClient weChatClient;

    @Test
    public void testGetSandboxSignKey() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("mch_id", weChatClient.getWeChatPayConfiguration().getMerchantId());
        map.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15));

        String sign = WeChatPaySignUtil.buildSign(map, weChatClient.getWeChatPayConfiguration().getApiSecretKey(), true);


        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        sb.append("<mch_id><![CDATA[").append(map.get("mch_id")).append("]]></mch_id>");
        sb.append("<nonce_str><![CDATA[").append(map.get("nonce_str")).append("]]></nonce_str>");
        sb.append("<sign><![CDATA[").append(sign).append("]]></sign>");
        sb.append("</xml>");

        String resultXml = weChatClient.pay().getSandboxSignKey(sb.toString());
        System.out.println(resultXml);
    }
}
