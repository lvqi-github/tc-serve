package com.tcxx.serve.core.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.MessageFormat;

@Getter
@AllArgsConstructor
public enum ResultCodeEnum {

    ERROR("-1", "系统异常"),
    SUCCESS("1000", "成功"),

    ERROR501("1501", "微信登录授权已过期，重新授权"),

    ERROR2001("2001", "Http请求异常"),
    ERROR2002("2002", "Http请求SSL安全连接创建异常"),

    ERROR2501("2501", "DataBase操作异常"),

    ERROR3001("3001", "微信([{0}])接口调用异常，微信errCode=[{1}]，微信errMsg=[{2}]"),

    ERROR4001("4001", "参数校验异常[{0}]"),

    ERROR4501("4501", "token签发者不合法");




    /** 编码 */
    private String code;
    /** 描述 */
    private String message;

    /**
     * 获取格式化消息
     * @param placeholder
     * @return
     */
    public String buildMessage(Object... placeholder) {
        return buildMessage(this, placeholder);
    }

    public static String buildMessage(ResultCodeEnum resultCode, Object... placeholder) {
        return MessageFormat.format(resultCode.getMessage(), placeholder);
    }
}
