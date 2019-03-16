package com.tcxx.serve.service.job;

import com.tcxx.serve.core.exception.WeChatInvokeRuntimeException;
import com.tcxx.serve.core.utils.BusinessUuidGenerateUtil;
import com.tcxx.serve.service.TcPublicAccountFocusService;
import com.tcxx.serve.service.entity.TcPublicAccountFocus;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.WeChatConfiguration;
import com.tcxx.serve.wechat.model.user.Followers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component("syncWeChatPublicAccountFocusOpenIdJob")
public class SyncWeChatPublicAccountFocusOpenIdJob {

    @Autowired
    private WeChatClient weChatClient;

    @Autowired
    private WeChatConfiguration weChatConfiguration;

    @Autowired
    private TcPublicAccountFocusService tcPublicAccountFocusService;

    public void execute() {
        try {
            // 从头开始获取，一次调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求。暂时不会超过10000用户，不考虑多次拉取。
            Followers followers = weChatClient.user().get(null);
            for (String openId : followers.getData().getOpenIdList()) {
                TcPublicAccountFocus publicAccountFocus = tcPublicAccountFocusService.
                        getByUuid(BusinessUuidGenerateUtil.getTcPublicAccountFocusUuid(weChatConfiguration.getPublicAccountWechatId(), openId));
                if (Objects.isNull(publicAccountFocus)) {
                    publicAccountFocus = new TcPublicAccountFocus();
                    publicAccountFocus.setPublicAccountWechatId(weChatConfiguration.getPublicAccountWechatId());
                    publicAccountFocus.setOpenId(openId);
                    tcPublicAccountFocusService.insert(publicAccountFocus);
                }
            }
        } catch (WeChatInvokeRuntimeException e) {
            log.error("SyncWeChatPublicAccountFocusOpenIdJob#WeChatInvokeRuntimeException error", e);
        }
    }

}
