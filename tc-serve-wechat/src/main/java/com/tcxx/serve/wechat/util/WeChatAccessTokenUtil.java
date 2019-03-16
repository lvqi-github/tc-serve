package com.tcxx.serve.wechat.util;

import com.tcxx.serve.wechat.WeChatConfiguration;
import lombok.Getter;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class WeChatAccessTokenUtil {

    private WeChatConfiguration weChatConfiguration;

    /**
     * 用于微信接入服务器有效性验证及消息事件验证
     */
    @Getter
    private String accessToken;


    @Autowired
    public WeChatAccessTokenUtil(WeChatConfiguration weChatConfiguration) {
        this.weChatConfiguration = weChatConfiguration;
        this.accessToken = weChatConfiguration.getAccessToken();
    }

    /**
     * 加密/校验流程如下：
     *
     * <p>
     * 1. 将token、timestamp、nonce三个参数进行字典序排序<br>
     * 2.将三个参数字符串拼接成一个字符串进行sha1加密<br>
     * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信<br>
     * </p>
     *
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数，nonce参数
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @return 验证成功返回true,否则返回false
     */
    public boolean checkSignature(String signature, String timestamp, String nonce) {
        List<String> params = new ArrayList<>();
        params.add(accessToken);
        params.add(timestamp);
        params.add(nonce);
        //1. 将token、timestamp、nonce三个参数进行字典序排序
        Collections.sort(params, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        //2. 将三个参数字符串拼接成一个字符串进行sha1加密
        String temp = DigestUtils.sha1Hex(params.get(0) + params.get(1) + params.get(2));
        //3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        return temp.equals(signature);
    }

}
