package com.tcxx.serve.wechat.model.pay;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@XStreamAlias("xml")
public class PayNotifyResult {

    /**
     * 返回状态码
     * 必填：是
     * 示例值：SUCCESS
     */
    @XStreamAlias("return_code")
    private String returnCode;
    /**
     * 返回信息
     * 必填：是
     * 示例值：OK
     */
    @XStreamAlias("return_msg")
    private String returnMsg;

    //*** 以下字段在return_code为SUCCESS的时候有返回 ***//

    /**
     * 微信分配的公众账号ID
     */
    @XStreamAlias("appid")
    private String appId;
    /**
     * 微信支付分配的商户号
     */
    @XStreamAlias("mch_id")
    private String mchId;
    /**
     * 微信支付分配的终端设备号
     */
    @XStreamAlias("device_info")
    private String deviceInfo;
    /**
     * 随机字符串，不长于32位
     */
    @XStreamAlias("nonce_str")
    private String nonceStr;
    /**
     * 微信返回的签名值
     */
    private String sign;
    /**
     * 签名类型，默认为MD5，支持HMAC-SHA256和MD5
     */
    @XStreamAlias("sign_type")
    private String signType;
    /**
     * 业务结果SUCCESS/FAIL
     */
    @XStreamAlias("result_code")
    private String resultCode;
    /**
     * 当result_code为FAIL时返回错误代码
     */
    @XStreamAlias("err_code")
    private String errCode;
    /**
     * 当result_code为FAIL时返回错误描述
     */
    @XStreamAlias("err_code_des")
    private String errCodeDes;
    /**
     * 用户标识，用户在商户appid下的唯一标识
     */
    @XStreamAlias("openid")
    private String openId;
    /**
     * 用户是否关注公众账号，Y-关注，N-未关注
     */
    @XStreamAlias("is_subscribe")
    private String isSubscribe;
    /**
     * 交易类型，JSAPI -JSAPI支付、NATIVE -Native支付、APP -APP支付
     */
    @XStreamAlias("trade_type")
    private String tradeType;
    /**
     * 付款银行
     */
    @XStreamAlias("bank_type")
    private String bankType;
    /**
     * 订单总金额，单位为分
     */
    @XStreamAlias("total_fee")
    private Integer totalFee;
    /**
     * 应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额
     */
    @XStreamAlias("settlement_total_fee")
    private Integer settlementTotalFee;
    /**
     * 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY
     */
    @XStreamAlias("fee_type")
    private String feeType;
    /**
     * 现金支付金额 订单现金支付金额
     */
    @XStreamAlias("cash_fee")
    private Integer cashFee;
    /**
     * 现金支付货币类型
     */
    @XStreamAlias("cash_fee_type")
    private String cashFeeType;
    /**
     * 微信支付订单号
     */
    @XStreamAlias("transaction_id")
    private String transactionId;
    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一
     */
    @XStreamAlias("out_trade_no")
    private String outTradeNo;
    /**
     * 商家数据包，原样返回
     */
    private String attach;
    /**
     * 支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
     */
    @XStreamAlias("time_end")
    private String timeEnd;

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("return_code", this.getReturnCode());
        map.put("return_msg", this.getReturnMsg());
        map.put("appid", this.getAppId());
        map.put("mch_id", this.getMchId());
        map.put("device_info", this.getDeviceInfo());
        map.put("nonce_str", this.getNonceStr());
        map.put("sign", this.getSign());
        map.put("sign_type", this.getSignType());
        map.put("result_code", this.getResultCode());
        map.put("err_code", this.getErrCode());
        map.put("err_code_des", this.getErrCodeDes());
        map.put("openid", this.getOpenId());
        map.put("is_subscribe", this.getIsSubscribe());
        map.put("trade_type", this.getTradeType());
        map.put("bank_type", this.getBankType());
        map.put("total_fee", String.valueOf(this.getTotalFee()));
        map.put("settlement_total_fee", String.valueOf(this.getSettlementTotalFee()));
        map.put("fee_type", this.getFeeType());
        map.put("cash_fee", String.valueOf(this.getCashFee()));
        map.put("cash_fee_type", this.getCashFeeType());
        map.put("transaction_id", this.getTransactionId());
        map.put("out_trade_no", this.getOutTradeNo());
        map.put("attach", this.getAttach());
        map.put("time_end", this.getTimeEnd());
        return map;
    }

    public boolean isSuccess() {
        if (returnCode.toUpperCase().equals("SUCCESS") && resultCode.toUpperCase().equals("SUCCESS")){
            return true;
        }
        return false;
    }
}
