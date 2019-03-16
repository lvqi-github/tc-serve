package com.tcxx.serve.service.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TemplateMessagePushStatusEnum {

    NOT_PUSH(1, "未推送"),
//    IN_THE_PUSH(2, "推送中"),
    PUSH_SUCCESS(3, "推送成功"),
    PUSH_FAILED(4, "推送失败")
//    PUSH_REJECTED(5, "推送被拒收")
    ;

    /** 状态 */
    private int status;

    /** 名称 */
    private String name;

    public static String getByStatus(int status) {
        for (TemplateMessagePushStatusEnum pushStatusEnum: TemplateMessagePushStatusEnum.values()){
            if (pushStatusEnum.getStatus() == status){
                return pushStatusEnum.getName();
            }
        }
        return null;
    }

    public static boolean contains(int status) {
        for (TemplateMessagePushStatusEnum pushStatusEnum: TemplateMessagePushStatusEnum.values()){
            if (pushStatusEnum.getStatus() == status){
                return true;
            }
        }
        return false;
    }

}
