package com.tcxx.serve.wechat.model.pay;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class WCPay {

    /**
     * 公众号id
     */
    private String appId;
    /**
     * 时间戳
     */
    private String timeStamp;
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 订单详情扩展字符串，统一下单接口返回的prepay_id参数值，提交格式如：prepay_id=123456789
     */
    private String packages;
    /**
     * 签名方式，签名类型，默认为MD5，支持HMAC-SHA256和MD5。注意此处需与统一下单的签名类型一致
     */
    private String signType;
    /**
     * 签名
     */
    private String paySign;

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("appId", this.getAppId());
        map.put("timeStamp", this.getTimeStamp());
        map.put("nonceStr", this.getNonceStr());
        map.put("package", this.getPackages());
        map.put("signType", this.getSignType());
        return map;
    }

}
