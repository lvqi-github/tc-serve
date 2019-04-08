package com.tcxx.serve.wechat.model.pay;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("xml")
public class CloseOrderResult {

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
     * 业务结果描述
     */
    @XStreamAlias("result_msg")
    private String resultMsg;
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

    public boolean isSuccess() {
        if (returnCode.toUpperCase().equals("SUCCESS") && resultCode.toUpperCase().equals("SUCCESS")){
            return true;
        }
        return false;
    }
}
