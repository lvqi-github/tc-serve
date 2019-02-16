package com.tcxx.serve.wechat.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tcxx.serve.core.exception.WeChatInvokeRuntimeException;
import com.tcxx.serve.core.http.HttpClientUtil;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.model.base.BaseAccessToken;

import java.util.HashMap;
import java.util.Map;

public class BaseComponent extends AbstractComponent {

    public BaseComponent(WeChatClient weChatClient) {
        super(weChatClient);
    }

    /**
     * 获取access_token（每次都获取新的，请缓存下来，2小时过期）
     *
     * @return 获取的AccessToken对象
     */
    public BaseAccessToken getBaseAccessToken() {

        //构建请求参数
        Map<String, Object> parms = new HashMap<>();
        parms.put("grant_type", "client_credential");
        parms.put("appid", weChatClient.getAppId());
        parms.put("secret", weChatClient.getAppSecret());

        JSONObject jsonObject = HttpClientUtil.doGet("https://api.weixin.qq.com/cgi-bin/token", parms);

        Object errCode = jsonObject.get("errcode");
        if (errCode != null) {
            //返回异常信息
            throw new WeChatInvokeRuntimeException(ResultCodeEnum.ERROR3001, "获取BaseAccessToken", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
        }
        return JSON.toJavaObject(jsonObject, BaseAccessToken.class);
    }
}
