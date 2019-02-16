package com.tcxx.serve.wechat.model.menu;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Button {

    private String name;

    private String type;

    @JSONField(name = "sub_button")
    private Button[] subButton;
}
