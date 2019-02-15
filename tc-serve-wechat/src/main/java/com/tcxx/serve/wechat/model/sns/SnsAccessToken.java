package com.tcxx.serve.wechat.model.sns;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class SnsAccessToken {

    @JSONField(name = "access_token")
    private String accessToken;

    @JSONField(name = "expires_in")
    private int expiresIn;

    @JSONField(name = "refresh_token")
    private String refreshToken;

    @JSONField(name = "openid")
    private String openId;

    private String scope;
}
