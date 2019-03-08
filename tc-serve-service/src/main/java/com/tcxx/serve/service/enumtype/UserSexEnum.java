package com.tcxx.serve.service.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserSexEnum {

    MAN(1, "男"),
    WOMAN(2, "女"),
    UNKNOWN(0, "未知");

    /** 类型 */
    private int type;

    /** 名称 */
    private String name;

    public static String getByType(int type) {
        for (UserSexEnum userSexEnum: UserSexEnum.values()){
            if (userSexEnum.getType() == type){
                return userSexEnum.getName();
            }
        }
        return null;
    }

    public static boolean contains(int type) {
        for (UserSexEnum userSexEnum: UserSexEnum.values()){
            if (userSexEnum.getType() == type){
                return true;
            }
        }
        return false;
    }

}
