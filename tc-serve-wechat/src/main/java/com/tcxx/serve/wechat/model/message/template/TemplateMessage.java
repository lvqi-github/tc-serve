package com.tcxx.serve.wechat.model.message.template;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Map;

@Data
public class TemplateMessage {

    /**
     * 接收者openId
     */
    @JSONField(name = "touser")
    private String toUser;
    /**
     * 模板ID
     */
    @JSONField(name = "template_id")
    private String templateId;
    /**
     * 模板跳转链接
     */
    private String url;
    /**
     * 跳小程序所需数据，不需跳小程序可不用传该数据
     */
    @JSONField(name = "miniprogram")
    private MiniProgram miniProgram;
    /**
     * 模板数据
     */
    private Map<String, TemplateData> data;

}
