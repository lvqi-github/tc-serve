package com.tcxx.serve.wechat.component;

import com.alibaba.fastjson.JSONObject;
import com.tcxx.serve.core.exception.WeChatInvokeRuntimeException;
import com.tcxx.serve.core.http.HttpClientUtil;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.model.user.Followers;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class UserComponent extends AbstractComponent {

    public UserComponent(WeChatClient weChatClient) {
        super(weChatClient);
    }

    /**
     * 获取用户列表
     *
     * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
     * @return 关注者列表对象
     */
    public Followers get(String nextOpenId) {

        //构建请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("access_token", weChatClient.getBaseAccessToken());
        if (StringUtils.isNotBlank(nextOpenId)){
            params.put("next_openid", nextOpenId);
        }

        JSONObject jsonObject = HttpClientUtil.doGet("https://api.weixin.qq.com/cgi-bin/user/get", params);

        Object errCode = jsonObject.get("errcode");
        if (errCode != null) {
            //返回异常信息
            throw new WeChatInvokeRuntimeException(ResultCodeEnum.ERROR3001, "获取关注用户列表", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
        }

        return JSONObject.toJavaObject(jsonObject, Followers.class);
    }

}
