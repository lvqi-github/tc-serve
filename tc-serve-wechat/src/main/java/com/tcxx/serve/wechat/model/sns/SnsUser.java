package com.tcxx.serve.wechat.model.sns;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class SnsUser {

    //用户的标识，对当前公众号唯一
    @JSONField(name = "openid")
    private String openId;

    //用户的昵称
    @JSONField(name = "nickname")
    private String nickName;

    //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private int sex;

    //用户所在省份
    private String province;

    //用户所在城市
    private String city;

    //用户所在国家
    private String country;

    //用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
    @JSONField(name = "headimgurl")
    private String headImgUrl;

    //只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
    @JSONField(name = "unionid")
    private String unionId;

}
