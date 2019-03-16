package com.tcxx.serve.wechat.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tcxx.serve.core.exception.WeChatInvokeRuntimeException;
import com.tcxx.serve.core.http.HttpClientUtil;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.model.message.template.TemplateMessage;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;

public class MessageComponent extends AbstractComponent {

    public MessageComponent(WeChatClient weChatClient) {
        super(weChatClient);
    }

    /**
     *
     * 发送模板消息(带跳转小程序或链接)
     *
     * <p>
     * 注：url和miniProgram都是非必填字段，若都不传则模板无跳转；若都传，会优先跳转至小程序。
     * 开发者可根据实际需要选择其中一种跳转方式即可。当用户的微信客户端版本不支持跳小程序时，将会跳转至url。
     * </p>
     *
     * @param templateMessage
     * @return msgId 微信消息id
     */
    public String sendTemplateMessage(TemplateMessage templateMessage) {
        if (Objects.isNull(templateMessage)) {
            throw new IllegalArgumentException("templateMessage can't be null");
        }
        if (StringUtils.isBlank(templateMessage.getToUser())) {
            throw new IllegalArgumentException("toUser can't be null or empty");
        }
        if (StringUtils.isBlank(templateMessage.getTemplateId())) {
            throw new IllegalStateException("templateId can not be null or empty");
        }
        if (templateMessage.getData() == null || templateMessage.getData().isEmpty()) {
            throw new IllegalStateException("data can not be null or empty");
        }

        JSONObject jsonObject = HttpClientUtil.doPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + weChatClient.getBaseAccessToken(), JSON.toJSON(templateMessage));

        Object errCode = jsonObject.get("errcode");
        if (errCode != null && !errCode.toString().equals("0")) {
            //返回异常信息
            throw new WeChatInvokeRuntimeException(ResultCodeEnum.ERROR3001, "发送模板消息", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
        }
        return jsonObject.getString("msgid");
    }

}

