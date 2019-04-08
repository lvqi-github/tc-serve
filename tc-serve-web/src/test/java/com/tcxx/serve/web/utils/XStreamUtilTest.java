package com.tcxx.serve.web.utils;

import com.tcxx.serve.core.utils.XStreamUtil;
import com.tcxx.serve.web.TcServeWebApplicationTests;
import com.tcxx.serve.wechat.model.pay.OrderQueryResult;
import com.tcxx.serve.wechat.model.pay.UnifiedOrderResult;
import org.junit.Test;

public class XStreamUtilTest extends TcServeWebApplicationTests {

    @Test
    public void testXmlToBean() {
        String xml = "<xml>\n" +
                "   <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "   <return_msg><![CDATA[OK]]></return_msg>\n" +
                "   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n" +
                "   <mch_id><![CDATA[10000100]]></mch_id>\n" +
                "   <device_info><![CDATA[1000]]></device_info>\n" +
                "   <nonce_str><![CDATA[TN55wO9Pba5yENl8]]></nonce_str>\n" +
                "   <sign><![CDATA[BDF0099C15FF7BC6B1585FBB110AB635]]></sign>\n" +
                "   <result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "   <openid><![CDATA[oUpF8uN95-Ptaags6E_roPHg7AG0]]></openid>\n" +
                "   <is_subscribe><![CDATA[Y]]></is_subscribe>\n" +
                "   <trade_type><![CDATA[MICROPAY]]></trade_type>\n" +
                "   <bank_type><![CDATA[CCB_DEBIT]]></bank_type>\n" +
                "   <total_fee>1</total_fee>\n" +
                "   <fee_type><![CDATA[CNY]]></fee_type>\n" +
                "   <transaction_id><![CDATA[1008450740201411110005820873]]></transaction_id>\n" +
                "   <out_trade_no><![CDATA[1415757673]]></out_trade_no>\n" +
                "   <attach><![CDATA[订单额外描述]]></attach>\n" +
                "   <time_end><![CDATA[20141111170043]]></time_end>\n" +
                "   <trade_state><![CDATA[SUCCESS]]></trade_state>\n" +
                "</xml>";

        OrderQueryResult orderQueryResult = XStreamUtil.getInstance().xmlToBean(xml, OrderQueryResult.class);
        System.out.println(orderQueryResult);


        String xml2 = "<xml>\n" +
                "   <appid>wx2421b1c4370ec43b</appid>\n" +
                "   <attach>支付测试</attach>\n" +
                "   <body>JSAPI支付测试</body>\n" +
                "   <mch_id>10000100</mch_id>\n" +
                "   <detail><![CDATA[{ \"goods_detail\":[ { \"goods_id\":\"iphone6s_16G\", \"wxpay_goods_id\":\"1001\", \"goods_name\":\"iPhone6s 16G\", \"quantity\":1, \"price\":528800, \"goods_category\":\"123456\", \"body\":\"苹果手机\" }, { \"goods_id\":\"iphone6s_32G\", \"wxpay_goods_id\":\"1002\", \"goods_name\":\"iPhone6s 32G\", \"quantity\":1, \"price\":608800, \"goods_category\":\"123789\", \"body\":\"苹果手机\" } ] }]]></detail>\n" +
                "   <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>\n" +
                "   <notify_url>http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php</notify_url>\n" +
                "   <openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid>\n" +
                "   <out_trade_no>1415659990</out_trade_no>\n" +
                "   <spbill_create_ip>14.23.150.211</spbill_create_ip>\n" +
                "   <total_fee>1</total_fee>\n" +
                "   <trade_type>JSAPI</trade_type>\n" +
                "   <sign>0CB01533B8C1EF103065174F50BCA001</sign>\n" +
                "</xml>";

        UnifiedOrderResult unifiedOrderResult = XStreamUtil.getInstance().xmlToBean(xml2, UnifiedOrderResult.class);
        System.out.println(unifiedOrderResult);
    }
}
