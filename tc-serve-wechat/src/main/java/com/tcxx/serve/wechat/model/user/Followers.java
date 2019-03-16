package com.tcxx.serve.wechat.model.user;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Followers {

    private int total;  //关注该公众账号的总用户数
    private int count;  //拉取的OPENID个数，最大值为10000
    private OpenIdData data;  //列表数据，OPENID的列表
    @JSONField(name = "next_openid")
    private String nextOpenId; //拉取列表的最后一个用户的OPENID
}
