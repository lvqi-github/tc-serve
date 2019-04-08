package com.tcxx.serve.wechat.model.pay;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付 关单
 */
@Data
public class CloseOrder {

    /**
     * 公众账号ID
     * 必填
     */
    private String appId;
    /**
     * 微信支付商户号
     * 必填
     */
    private String mchId;
    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一
     */
    private String outTradeNo;
    /**
     * 随机字符串，长度要求在32位以内
     * 必填
     */
    private String nonceStr;
    /**
     * 通过签名算法计算得出的签名值
     * 必填
     */
    private String sign;
    /**
     * 签名类型，默认为MD5，支持HMAC-SHA256和MD5
     * 不必填
     */
    private String signType;

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", this.getAppId());
        map.put("mch_id", this.getMchId());
        map.put("out_trade_no", this.getOutTradeNo());
        map.put("nonce_str", this.getNonceStr());
        return map;
    }

    public String toXML() {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        sb.append("<appid><![CDATA[").append(this.getAppId()).append("]]></appid>");
        sb.append("<mch_id><![CDATA[").append(this.getMchId()).append("]]></mch_id>");
        sb.append("<out_trade_no><![CDATA[").append(this.getOutTradeNo()).append("]]></out_trade_no>");
        sb.append("<nonce_str><![CDATA[").append(this.getNonceStr()).append("]]></nonce_str>");
        sb.append("<sign><![CDATA[").append(this.getSign()).append("]]></sign>");
        sb.append("</xml>");
        return sb.toString();
    }
}
