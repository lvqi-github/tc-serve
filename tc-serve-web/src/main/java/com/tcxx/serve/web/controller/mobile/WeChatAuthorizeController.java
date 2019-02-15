package com.tcxx.serve.web.controller.mobile;

import com.tcxx.serve.core.annotation.PassTokenValidation;
import com.tcxx.serve.wechat.WeChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mobile/weChatAuth")
public class WeChatAuthorizeController {

    @Autowired
    private WeChatClient weChatClient;

    @PassTokenValidation
    @RequestMapping("/getAuthorizeUrl")
    public Map<String, Object> getAuthorizeUrl() {
        Map<String, Object> map = new HashMap<>();
        map.put("resultCode", 0);
        map.put("resultMsg", "成功");
        map.put("data", weChatClient.sns().getOAuth2CodeUserInfoUrl("http://www.tcxx.com/#/auth"));
        return map;
    }
}
