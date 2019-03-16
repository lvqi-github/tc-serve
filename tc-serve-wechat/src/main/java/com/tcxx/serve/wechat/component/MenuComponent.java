package com.tcxx.serve.wechat.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tcxx.serve.core.exception.WeChatInvokeRuntimeException;
import com.tcxx.serve.core.http.HttpClientUtil;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.model.menu.Menu;

public class MenuComponent extends AbstractComponent {

    public MenuComponent(WeChatClient weChatClient) {
        super(weChatClient);
    }

    /**
     * 创建自定义菜单
     *
     * @param menu 菜单对象
     */
    public void create(Menu menu) {

        JSONObject jsonObject = HttpClientUtil.doPost("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + weChatClient.getBaseAccessToken(), JSON.toJSON(menu));

        Object errCode = jsonObject.get("errcode");
        if (errCode != null && !errCode.toString().equals("0")) {
            //返回异常信息
            throw new WeChatInvokeRuntimeException(ResultCodeEnum.ERROR3001, "创建自定义菜单", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
        }
    }
}
