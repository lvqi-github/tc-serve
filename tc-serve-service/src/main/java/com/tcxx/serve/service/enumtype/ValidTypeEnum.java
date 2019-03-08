package com.tcxx.serve.service.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValidTypeEnum {

    VALID(1, "有效"),
    INVALID(2, "未生效");

    /** 类型 */
    private int type;

    /** 名称 */
    private String name;

    public static String getByType(int type) {
        for (ValidTypeEnum validTypeEnum: ValidTypeEnum.values()){
            if (validTypeEnum.getType() == type){
                return validTypeEnum.getName();
            }
        }
        return null;
    }

    public static boolean contains(int type) {
        for (ValidTypeEnum validTypeEnum: ValidTypeEnum.values()){
            if (validTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }

}
