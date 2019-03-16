package com.tcxx.serve.service.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TemplateMessagePushTypeEnum {

    ARTICLE_SUBSCRIBE_PUSH(1, "文章订阅推送", "SAowgybTTTarsqWlwSp1v3FjfxMC24C8h4f0TPA2PCM", "https://www.baidu.com?articleId=");

    /** 类型 */
    private int type;

    /** 名称 */
    private String name;

    /** 模板消息id */
    private String templateId;

    /** 跳转url前缀 */
    private String urlPrefix;

    public static String getByType(int type) {
        for (TemplateMessagePushTypeEnum pushTypeEnum: TemplateMessagePushTypeEnum.values()){
            if (pushTypeEnum.getType() == type){
                return pushTypeEnum.getName();
            }
        }
        return null;
    }

    public static boolean contains(int type) {
        for (TemplateMessagePushTypeEnum pushTypeEnum: TemplateMessagePushTypeEnum.values()){
            if (pushTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }

}
