package com.tcxx.serve.web.controller.admin;

import com.tcxx.serve.core.annotation.AdminLoginTokenValidation;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.enumtype.ButtonTypeEnum;
import com.tcxx.serve.wechat.enumtype.ClickEventKeyEnum;
import com.tcxx.serve.wechat.model.menu.Button;
import com.tcxx.serve.wechat.model.menu.ClickButton;
import com.tcxx.serve.wechat.model.menu.Menu;
import com.tcxx.serve.wechat.model.menu.ViewButton;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/weChatManager")
public class WeChatManagerController {

    @Autowired
    private WeChatClient weChatClient;

    @AdminLoginTokenValidation
    @RequestMapping(value = "/initWeChatMenu", method = RequestMethod.POST)
    public Result<Boolean> initWeChatMenu() {
        Menu menu = new Menu();

        ViewButton viewButton1 = new ViewButton();
        viewButton1.setName("进入首页");
        viewButton1.setType(ButtonTypeEnum.VIEW.getType());
        viewButton1.setUrl("https://tqh.grtcxx.com");

        ClickButton clickButton1 = new ClickButton();
        clickButton1.setName("联系客服");
        clickButton1.setType(ButtonTypeEnum.CLICK.getType());
        clickButton1.setKey(ClickEventKeyEnum.CONTACT_CUSTOMER_SERVICE.getKey());

        menu.setButton(new Button[]{viewButton1, clickButton1});

        weChatClient.menu().create(menu);

        Result<Boolean> result = ResultBuild.wrapSuccess();
        result.setValue(true);
        return result;
    }

}
