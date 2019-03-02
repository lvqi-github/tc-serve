package com.tcxx.serve.service.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArticleTypeEnum {

    FOOTBALL(1, "足球"),
    BASKETBALL(2, "篮球"),
    FOOTBALL_BASKETBALL(3, "足篮混合");

    /** 类型 */
    private int type;

    /** 名称 */
    private String name;

    public static String getByType(int type) {
        for (ArticleTypeEnum articleTypeEnum: ArticleTypeEnum.values()){
            if (articleTypeEnum.getType() == type){
                return articleTypeEnum.getName();
            }
        }
        return null;
    }

    public static boolean contains(int type) {
        for (ArticleTypeEnum articleTypeEnum: ArticleTypeEnum.values()){
            if (articleTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }

}
