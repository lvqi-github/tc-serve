package com.tcxx.serve.web.wechat;

import com.tcxx.serve.core.utils.XStreamUtil;
import com.tcxx.serve.web.TcServeWebApplicationTests;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.model.pay.PayNotifyResult;
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

    @Test
    public void testSignBuild() {

        String requestBody = "<xml><appid><![CDATA[wxb642cfbf1801eb61]]></appid>\n" +
                "<bank_type><![CDATA[CMB_CREDIT]]></bank_type>\n" +
                "<cash_fee><![CDATA[11]]></cash_fee>\n" +
                "<fee_type><![CDATA[CNY]]></fee_type>\n" +
                "<is_subscribe><![CDATA[Y]]></is_subscribe>\n" +
                "<mch_id><![CDATA[1530975061]]></mch_id>\n" +
                "<nonce_str><![CDATA[ab83b5a616a043d]]></nonce_str>\n" +
                "<openid><![CDATA[oSs_41Vrqo7rlKWSNIRZxV0m2JJk]]></openid>\n" +
                "<out_trade_no><![CDATA[3175265027079680]]></out_trade_no>\n" +
                "<result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "<return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "<sign><![CDATA[E08BDA382ED3E888AA1AA9374FB09DC6]]></sign>\n" +
                "<time_end><![CDATA[20190409132201]]></time_end>\n" +
                "<total_fee>11</total_fee>\n" +
                "<trade_type><![CDATA[JSAPI]]></trade_type>\n" +
                "<transaction_id><![CDATA[4200000295201904099044436816]]></transaction_id>\n" +
                "</xml>";

        // 解析请求
        PayNotifyResult payNotifyResult = XStreamUtil.getInstance().xmlToBean(requestBody, PayNotifyResult.class);
        System.out.println(payNotifyResult);
        // 校验消息是否合法
        String sign = WeChatPaySignUtil.buildSign(payNotifyResult.toMap(), weChatClient.getWeChatPayConfiguration().getApiSecretKey(), true);
        System.out.println(sign);
    }
}
