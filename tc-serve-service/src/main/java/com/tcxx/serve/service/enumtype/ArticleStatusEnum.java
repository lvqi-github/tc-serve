package com.tcxx.serve.service.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  ArticleStatusEnum {

    NOT_STARTED(1, "未开始"),
    ONGOING(2, "进行中"),
    FINISHED(3, "已结束");

    /** 状态 */
    private int status;

    /** 名称 */
    private String name;

    public static String getByStatus(int status) {
        for (ArticleStatusEnum articleStatusEnum: ArticleStatusEnum.values()){
            if (articleStatusEnum.getStatus() == status){
                return articleStatusEnum.getName();
            }
        }
        return null;
    }

    public static boolean contains(int status) {
        for (ArticleStatusEnum articleStatusEnum: ArticleStatusEnum.values()){
            if (articleStatusEnum.getStatus() == status){
                return true;
            }
        }
        return false;
    }
}
