package com.tcxx.serve.wechat.model.pay;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@XStreamAlias("xml")
public class UnifiedOrderResult {

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
     * 自定义参数，可以为请求支付的终端设备号等
     */
    @XStreamAlias("device_info")
    private String deviceInfo;
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

    //*** 以下字段在return_code 和result_code都为SUCCESS的时候有返回 ***//
    /**
     * 交易类型，JSAPI -JSAPI支付、NATIVE -Native支付、APP -APP支付
     */
    @XStreamAlias("trade_type")
    private String tradeType;
    /**
     * 预支付交易会话标识
     * 微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时
     */
    @XStreamAlias("prepay_id")
    private String prepayId;
    /**
     * 二维码连接
     * trade_type=NATIVE时有返回，此url用于生成支付二维码，然后提供给用户进行扫码支付。
     * 注意：code_url的值并非固定，使用时按照URL格式转成二维码即可
     */
    @XStreamAlias("code_url")
    private String codeUrl;

    public boolean isSuccess() {
        if (returnCode.toUpperCase().equals("SUCCESS") && resultCode.toUpperCase().equals("SUCCESS")){
            return true;
        }
        return false;
    }
}
