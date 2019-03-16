package com.tcxx.serve.web.wechat;

import com.tcxx.serve.web.TcServeWebApplicationTests;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.model.base.BaseAccessToken;
import com.tcxx.serve.wechat.model.user.Followers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserComponentTest extends TcServeWebApplicationTests {

    @Autowired
    private WeChatClient weChatClient;

    @Test
    public void testGet() {
        BaseAccessToken baseAccessToken = weChatClient.base().getBaseAccessToken();
        System.out.println(baseAccessToken);

        Followers followers = weChatClient.user().get(null);
        System.out.println(followers);
    }
}
