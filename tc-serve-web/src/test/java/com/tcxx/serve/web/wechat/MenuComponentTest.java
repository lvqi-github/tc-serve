package com.tcxx.serve.web.wechat;

import com.tcxx.serve.web.TcServeWebApplicationTests;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.enumtype.ButtonTypeEnum;
import com.tcxx.serve.wechat.model.menu.Button;
import com.tcxx.serve.wechat.model.menu.ClickButton;
import com.tcxx.serve.wechat.model.menu.Menu;
import com.tcxx.serve.wechat.model.menu.ViewButton;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MenuComponentTest extends TcServeWebApplicationTests {

    @Autowired
    private WeChatClient weChatClient;

    @Test
    public void testCreateMenu() {
        Menu menu = new Menu();

        ViewButton viewButton1 = new ViewButton();
        viewButton1.setName("最新推荐");
        viewButton1.setType(ButtonTypeEnum.VIEW.getType());
        viewButton1.setUrl("http://23g698m374.iask.in/#/index");

        ClickButton clickButton1 = new ClickButton();
        clickButton1.setName("订阅消息");
        clickButton1.setType(ButtonTypeEnum.CLICK.getType());
        clickButton1.setKey("subscribe_message");

        ClickButton clickButton2 = new ClickButton();
        clickButton2.setName("取消订阅");
        clickButton2.setType(ButtonTypeEnum.CLICK.getType());
        clickButton2.setKey("unsubscribe_message");

        ClickButton clickButton3 = new ClickButton();
        clickButton3.setName("联系客服");
        clickButton3.setType(ButtonTypeEnum.CLICK.getType());
        clickButton3.setKey("contact_customer_service");

        Button button = new Button();
        button.setName("客服/订阅");
        button.setSubButton(new  Button[]{clickButton1, clickButton2, clickButton3});

        menu.setButton(new Button[]{viewButton1, button});

        weChatClient.menu().create(menu);
    }

}
