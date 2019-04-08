package com.tcxx.serve.core.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.MessageFormat;

@Getter
@AllArgsConstructor
public enum ResultCodeEnum {

    ERROR("-1", "系统异常"),
    SUCCESS("1000", "成功"),

    ERROR1501("1501", "微信登录授权已过期，重新授权"),
    ERROR1502("1502", "用户名/密码错误"),

    ERROR2001("2001", "Http请求异常"),
    ERROR2002("2002", "Http请求SSL安全连接创建异常"),

    ERROR2501("2501", "DataBase操作异常"),

    ERROR3001("3001", "微信([{0}])接口调用异常，微信errCode=[{1}]，微信errMsg=[{2}]"),
    ERROR3002("3002", "微信支付([{0}])接口调用异常"),

    ERROR4001("4001", "参数校验异常[{0}]"),

    ERROR4501("4501", "token签发者不合法"),
    ERROR4502("4502", "token失效，重新登陆"),

    ERROR5001("5001", "七牛云文件操作异常"),
    ERROR5002("5002", "文件操作异常"),

    // ---------------- admin business result code----------------------------------------------------------------------------------
    ERROR10001("10001", "文章推送任务生成失败");

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
