package com.tcxx.serve.web.controller.admin;

import com.tcxx.serve.core.annotation.PassRequestSignValidation;
import com.tcxx.serve.core.annotation.PassTokenValidation;
import com.tcxx.serve.service.handler.DefaultMessageHandler;
import com.tcxx.serve.wechat.util.WeChatAccessTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/weChatAccess")
public class WeChatAccessController {

    @Autowired
    private WeChatAccessTokenUtil weChatAccessTokenUtil;

    @Autowired
    private DefaultMessageHandler defaultMessageHandler;

    /**
     * 开发者接入验证
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @PassRequestSignValidation
    @PassTokenValidation
    @RequestMapping(method = RequestMethod.GET)
    public String weChatAccessGet(@RequestParam(name = "signature") String signature,
                                  @RequestParam(name = "timestamp") String timestamp,
                                  @RequestParam(name = "nonce") String nonce,
                                  @RequestParam(name = "echostr") String echostr) {
        if (weChatAccessTokenUtil.checkSignature(signature, timestamp, nonce)){
            return echostr;
        }
        return null;
    }

    @PassRequestSignValidation
    @PassTokenValidation
    @RequestMapping(method = RequestMethod.POST)
    public String weChatAccessPost(@RequestBody String requestBody,
                                   @RequestParam(name = "signature") String signature,
                                   @RequestParam(name = "timestamp") String timestamp,
                                   @RequestParam(name = "nonce") String nonce) {
        if (!weChatAccessTokenUtil.checkSignature(signature, timestamp, nonce)){
            return null;
        }
        log.info("requestBody=" + requestBody);
        return defaultMessageHandler.invoke(requestBody);
    }

}
