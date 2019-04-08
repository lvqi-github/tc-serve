package com.tcxx.serve.wechat.model.pay;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("xml")
public class OrderQueryResult {

    /**
     * 返回状态码
     * 必填：是
     * 值：SUCCESS/FAIL
     * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
     */
    @XStreamAlias("return_code")
    private String returnCode;
    /**
     * 返回信息
     * 必填：是
     * 当return_code为FAIL时返回信息为错误原因 ，例如：签名失败、参数格式校验错误
     */
    @XStreamAlias("return_msg")
    private String returnMsg;

    //*** 以下字段在return_code为SUCCESS的时候有返回 ***//

    /**
     * 调用接口提交的公众账号ID
     */
    @XStreamAlias("appid")
    private String appId;
    /**
     * 调用接口提交的商户号
     */
    @XStreamAlias("mch_id")
    private String mchId;
    /**
     * 微信返回的随机字符串
     */
    @XStreamAlias("nonce_str")
    private String nonceStr;
    /**
     * 微信返回的签名值
     */
    private String sign;
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

    // 以下字段在return_code 、result_code、trade_state都为SUCCESS时有返回 ，如trade_state不为 SUCCESS，则只返回out_trade_no（必传）和attach（选传）。

    /**
     * 自定义参数，可以为请求支付的终端设备号等
     */
    @XStreamAlias("device_info")
    private String deviceInfo;
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
     * 交易状态
     * SUCCESS—支付成功、REFUND—转入退款、NOTPAY—未支付、CLOSED—已关闭、
     * REVOKED—已撤销（付款码支付）、USERPAYING--用户支付中（付款码支付）、
     * PAYERROR--支付失败(其他原因，如银行返回失败)
     */
    @XStreamAlias("trade_state")
    private String tradeState;
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
    /**
     * 交易状态描述，对当前查询订单状态的描述和下一步操作的指引
     */
    @XStreamAlias("trade_state_desc")
    private String tradeStateDesc;
}
