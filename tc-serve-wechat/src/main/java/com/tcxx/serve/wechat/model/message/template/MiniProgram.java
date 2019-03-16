package com.tcxx.serve.wechat.model.message.template;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class MiniProgram {

    /**
     * 所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系）
     */
    @JSONField(name = "appid")
    private String appId;
    /**
     * 所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar）
     */
    @JSONField(name = "pagepath")
    private String pagePath;

}
