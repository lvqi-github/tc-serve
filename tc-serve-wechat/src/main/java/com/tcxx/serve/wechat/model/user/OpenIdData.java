package com.tcxx.serve.wechat.model.user;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class OpenIdData {

    @JSONField(name = "openid")
    private List<String> openIdList;  //列表数据，OPENID的列表

}
