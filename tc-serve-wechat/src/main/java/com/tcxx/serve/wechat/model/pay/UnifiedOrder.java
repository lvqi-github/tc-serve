package com.tcxx.serve.wechat.model.pay;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付 统一下单
 */
@Data
public class UnifiedOrder {

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
     * 设备号 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
     * 不必填
     */
    private String deviceInfo;
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
    /**
     * 商品描述
     * 微信浏览器-公众号支付 格式为：商家名称-销售商品类目 例如：腾讯-游戏
     * 必填
     */
    private String body;
    /**
     * 商品详情
     * 不必填
     */
    private String detail;
    /**
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
     * 不必填
     */
    private String attach;
    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一
     * 必填
     */
    private String outTradeNo;
    /**
     * 标价币种，符合ISO 4217标准的三位字母代码，默认人民币：CNY
     * 不必填
     */
    private String feeType;
    /**
     * 订单总金额，单位为分
     * 必填
     */
    private Integer totalFee;
    /**
     * 终端IP，支持IPV4和IPV6两种格式的IP地址。调用微信支付API的机器IP
     * 必填
     */
    private String spbillCreateIp;
    /**
     * 交易起始时间，订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
     * 不必填
     */
    private String timeStart;
    /**
     * 交易结束时间，订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。
     * 订单失效时间是针对订单号而言的，由于在请求支付的时候有一个必传参数prepay_id只有两小时的有效期，
     * 所以在重入时间超过2小时的时候需要重新请求下单接口获取新的prepay_id
     * 不必填
     */
    private String timeExpire;
    /**
     * 订单优惠标记，使用代金券或立减优惠功能时需要的参数
     * 不必填
     */
    private String goodsTag;
    /**
     * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数
     * 必填
     */
    private String notifyUrl;
    /**
     * 交易类型，JSAPI -JSAPI支付、NATIVE -Native支付、APP -APP支付
     * 必填
     */
    private String tradeType;
    /**
     * 商品ID，trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义
     * 不必填
     */
    private String productId;
    /**
     * 指定支付方式，上传此参数no_credit--可限制用户不能使用信用卡支付
     * 不必填
     */
    private String limitPay;
    /**
     * 用户标识，trade_type=JSAPI时（即JSAPI支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识
     * 不必填
     */
    private String openId;

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("appid", this.getAppId());
        map.put("mch_id", this.getMchId());
        map.put("nonce_str", this.getNonceStr());
        map.put("body", this.getBody());
        map.put("out_trade_no", this.getOutTradeNo());
        map.put("total_fee", this.getTotalFee() != null ? String.valueOf(this.getTotalFee()) : null);
        map.put("spbill_create_ip", this.getSpbillCreateIp());
        map.put("notify_url", this.getNotifyUrl());
        map.put("trade_type", this.getTradeType());
        map.put("openid", this.getOpenId());
        return map;
    }

    public String toXML() {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        sb.append("<appid><![CDATA[").append(this.getAppId()).append("]]></appid>");
        sb.append("<mch_id><![CDATA[").append(this.getMchId()).append("]]></mch_id>");
        sb.append("<nonce_str><![CDATA[").append(this.getNonceStr()).append("]]></nonce_str>");
        sb.append("<sign><![CDATA[").append(this.getSign()).append("]]></sign>");
        sb.append("<body><![CDATA[").append(this.getBody()).append("]]></body>");
        sb.append("<out_trade_no><![CDATA[").append(this.getOutTradeNo()).append("]]></out_trade_no>");
        sb.append("<total_fee><![CDATA[").append(this.getTotalFee()).append("]]></total_fee>");
        sb.append("<spbill_create_ip><![CDATA[").append(this.getSpbillCreateIp()).append("]]></spbill_create_ip>");
        sb.append("<notify_url><![CDATA[").append(this.getNotifyUrl()).append("]]></notify_url>");
        sb.append("<trade_type><![CDATA[").append(this.getTradeType()).append("]]></trade_type>");
        sb.append("<openid><![CDATA[").append(this.getOpenId()).append("]]></openid>");
        sb.append("</xml>");
        return sb.toString();
    }

}
