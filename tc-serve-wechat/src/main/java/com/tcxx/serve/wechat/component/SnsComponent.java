package com.tcxx.serve.wechat.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tcxx.serve.core.exception.WeChatInvokeRuntimeException;
import com.tcxx.serve.core.http.HttpClientUtil;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.WeChatErrorCode;
import com.tcxx.serve.wechat.model.sns.SnsAccessToken;
import com.tcxx.serve.wechat.model.sns.SnsUser;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class SnsComponent extends AbstractComponent {

    /**
     * 默认授权请求URL
     */
    private String authorize_url = "https://open.weixin.qq.com/connect/oauth2/authorize";

    public SnsComponent(WeChatClient weChatClient) {
        super(weChatClient);
    }

    /**
     * 静默授权获取openid
     *
     * @param redirect_uri 授权后重定向的回调链接地址（不用编码）
     * @return 静默授权链接
     */
    public String getOAuth2CodeBaseUrl(String redirect_uri) {
        return getOAuth2CodeUrl(redirect_uri, "snsapi_base", "DEFAULT");
    }

    /**
     * 网页安全授权获取用户信息
     *
     * @param redirect_uri 授权后重定向的回调链接地址（不用编码）
     * @return 网页安全授权链接
     */
    public String getOAuth2CodeUserInfoUrl(String redirect_uri) {
        return getOAuth2CodeUrl(redirect_uri, "snsapi_userinfo", "DEFAULT");
    }

    /**
     * 获取授权链接
     *
     * @param redirect_uri 授权后重定向的回调链接地址（不用编码）
     * @param scope 应用授权作用域
     * snsapi_base（不弹出授权页面，直接跳转，只能获取用户openid）
     * snsapi_userinfo（弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 ）
     * @param state 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     * @return 授权链接
     */
    public String getOAuth2CodeUrl(String redirect_uri, String scope, String state) {
        return authorize_url + "?appid=" + weChatClient.getAppId() + "&redirect_uri=" + redirect_uri + "&response_type=code&scope=" + scope + "&state=" + state + "#wechat_redirect";
    }

    /**
     * 获取网页授权AccessToken
     *
     * @param code
     * @return 网页授权AccessToken
     * @since 0.1.0
     */
    public SnsAccessToken getSnsOAuth2AccessToken(String code) {
        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("code can't be null or empty");
        }
        //构建请求参数
        Map<String, Object> parms = new LinkedHashMap<>();
        parms.put("appid", weChatClient.getAppId());
        parms.put("secret", weChatClient.getAppSecret());
        parms.put("code", code);
        parms.put("grant_type", "authorization_code");

        JSONObject jsonObject = HttpClientUtil.doGet("https://api.weixin.qq.com/sns/oauth2/access_token", parms);

        Object errCode = jsonObject.get("errcode");
        if (errCode != null) {
            //返回异常信息
            throw new WeChatInvokeRuntimeException(ResultCodeEnum.ERROR3001, "根据code获取网页授权accessToken", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
        }
        return JSON.toJavaObject(jsonObject, SnsAccessToken.class);
    }

    /**
     * 检验授权凭证（access_token）是否有效
     *
     * @param access_token 网页授权接口调用凭证
     * @param openid 用户的唯一标识
     * @return 可用返回true，否则返回false
     * @since 0.1.0
     */
    public boolean validateAccessToken(String access_token, String openid) {
        if (StringUtils.isBlank(access_token)) {
            throw new IllegalArgumentException("access_token can't be null or empty");
        }
        if (StringUtils.isBlank(openid)) {
            throw new IllegalArgumentException("openid can't be null or empty");
        }
        //构建请求参数
        Map<String, Object> parms = new HashMap<>();
        parms.put("access_token", access_token);
        parms.put("openid", openid);

        JSONObject jsonObject = HttpClientUtil.doGet("https://api.weixin.qq.com/sns/auth", parms);

        return jsonObject.getIntValue("errcode") == 0;
    }

    /**
     * 刷新用户网页授权AccessToken
     *
     * @param refresh_token 用户刷新access_token
     * @return 刷新后的用户网页授权AccessToken
     * @since 0.1.0
     */
    public SnsAccessToken refreshToken(String refresh_token) {
        if (StringUtils.isBlank(refresh_token)) {
            throw new IllegalArgumentException("refresh_token can't be null or empty");
        }
        //构建请求参数
        Map<String, Object> parms = new HashMap<>();
        parms.put("appid", weChatClient.getAppId());
        parms.put("grant_type", "refresh_token");
        parms.put("refresh_token", refresh_token);

        JSONObject jsonObject = HttpClientUtil.doGet("https://api.weixin.qq.com/sns/oauth2/refresh_token", parms);

        Object errCode = jsonObject.get("errcode");
        if (errCode != null) {
            //返回异常信息
            throw new WeChatInvokeRuntimeException(ResultCodeEnum.ERROR3001, "刷新网页授权accessToken", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
        }
        return JSON.toJavaObject(jsonObject, SnsAccessToken.class);
    }

    /**
     * 拉取用户信息
     *
     * @param access_token 网页授权接口调用凭证
     * @param openid 用户的唯一标识
     * @return 用户对象
     */
    public SnsUser getSnsUser(String access_token, String openid) {
        if (StringUtils.isBlank(access_token)) {
            throw new IllegalArgumentException("access_token can't be null or empty");
        }
        if (StringUtils.isBlank(openid)) {
            throw new IllegalArgumentException("openid can't be null or empty");
        }
        //构建请求参数
        Map<String, Object> parms = new HashMap<>();
        parms.put("access_token", access_token);
        parms.put("openid", openid);
        parms.put("lang", "zh_CN"); // 国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语

        JSONObject jsonObject = HttpClientUtil.doGet("https://api.weixin.qq.com/sns/userinfo", parms);

        Object errCode = jsonObject.get("errcode");
        if (errCode != null) {
            //返回异常信息
            throw new WeChatInvokeRuntimeException(ResultCodeEnum.ERROR3001, "拉取用户信息", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
        }
        return JSON.toJavaObject(jsonObject, SnsUser.class);
    }

    /**
     * 拉取用户信息
     *
     * @param code
     * @return 用户对象
     */
    public SnsUser getSnsUserByCode(String code) {
        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("code can't be null or empty");
        }
        SnsAccessToken snsAccessToken = getSnsOAuth2AccessToken(code);
        return getSnsUser(snsAccessToken.getAccessToken(), snsAccessToken.getOpenId());
    }

}
