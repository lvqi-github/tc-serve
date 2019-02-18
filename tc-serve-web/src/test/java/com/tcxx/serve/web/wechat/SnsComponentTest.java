package com.tcxx.serve.web.wechat;

import com.tcxx.serve.web.TcServeWebApplicationTests;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.model.sns.SnsAccessToken;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SnsComponentTest extends TcServeWebApplicationTests {

    @Autowired
    private WeChatClient weChatClient;

    @Test
    public void testRefreshToken() {
        String refreshToken = "18_O6DodHtP4RZioASyX8el561ppx6V80jBbS2r3JBwrkM-tVJ7c2Kx01IiSlQlUzbrERHaw4fvx39VrOgW0T6fDpLdsduCM9UvX5BZk-ba8-g";
        System.out.println(refreshToken);
        SnsAccessToken snsAccessToken = weChatClient.sns().refreshToken(refreshToken);
        System.out.println(snsAccessToken.getRefreshToken());
        System.out.println(snsAccessToken.getAccessToken());
    }
}
