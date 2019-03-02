package com.tcxx.serve.service.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HitRecordTypeEnum {

    DATE(1, "按日期"),
    AUTHOR(2, "按作者");

    /** 类型 */
    private int type;

    /** 名称 */
    private String name;

    public static String getByType(int type) {
        for (HitRecordTypeEnum hitRecordTypeEnum: HitRecordTypeEnum.values()){
            if (hitRecordTypeEnum.getType() == type){
                return hitRecordTypeEnum.getName();
            }
        }
        return null;
    }

    public static boolean contains(int type) {
        for (HitRecordTypeEnum hitRecordTypeEnum: HitRecordTypeEnum.values()){
            if (hitRecordTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }
}
