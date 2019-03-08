package com.tcxx.serve.service.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnableStatusEnum {

    ENABLE(1, "已启用"),
    DISABLE(2, "未启用");

    /** 状态 */
    private int status;

    /** 名称 */
    private String name;

    public static String getByStatus(int status) {
        for (EnableStatusEnum enableStatusEnum: EnableStatusEnum.values()){
            if (enableStatusEnum.getStatus() == status){
                return enableStatusEnum.getName();
            }
        }
        return null;
    }

    public static boolean contains(int status) {
        for (EnableStatusEnum enableStatusEnum: EnableStatusEnum.values()){
            if (enableStatusEnum.getStatus() == status){
                return true;
            }
        }
        return false;
    }
}
