package com.tcxx.serve.service.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArticleChargeTypeEnum {

    CHARGE(1, "收费"),
    PUBLIC(2, "公开");

    /** 类型 */
    private int type;

    /** 名称 */
    private String name;

    public static String getByType(int type) {
        for (ArticleChargeTypeEnum articleChargeTypeEnum: ArticleChargeTypeEnum.values()){
            if (articleChargeTypeEnum.getType() == type){
                return articleChargeTypeEnum.getName();
            }
        }
        return null;
    }

    public static boolean contains(int type) {
        for (ArticleChargeTypeEnum articleChargeTypeEnum: ArticleChargeTypeEnum.values()){
            if (articleChargeTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }

}
