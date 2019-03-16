package com.tcxx.serve.core.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 业务防重uuid 生成工具
 */
public class BusinessUuidGenerateUtil {

    public static String getTcPublicAccountFocusUuid(String publicAccountWechatId, String openId) {
        if (StringUtils.isBlank(publicAccountWechatId)) {
            throw new IllegalArgumentException("publicAccountWechatId can't be null or empty");
        }
        if (StringUtils.isBlank(openId)) {
            throw new IllegalArgumentException("openId can't be null or empty");
        }

        return DigestUtils.md5Hex(String.format("%s_%s", publicAccountWechatId, openId));
    }

    public static String getTcTemplateMessagePushUuid(Integer pushType, String businessId, String userId, String openId) {
        if (pushType == null) {
            throw new IllegalArgumentException("pushType can't be null or empty");
        }
        if (StringUtils.isBlank(businessId)) {
            throw new IllegalArgumentException("businessId can't be null or empty");
        }
        if (StringUtils.isBlank(userId)) {
            throw new IllegalArgumentException("userId can't be null or empty");
        }
        if (StringUtils.isBlank(openId)) {
            throw new IllegalArgumentException("openId can't be null or empty");
        }

        return DigestUtils.md5Hex(String.format("%s_%s_%s_%s", pushType, businessId, userId, openId));
    }
}
