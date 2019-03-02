package com.tcxx.serve.service.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArticleReleaseStatusEnum {

    RELEASE(1, "已发布"),
    NO_RELEASE(2, "未发布");

    /** 状态 */
    private int status;

    /** 名称 */
    private String name;

    public static String getByStatus(int status) {
        for (ArticleReleaseStatusEnum releaseStatusEnum: ArticleReleaseStatusEnum.values()){
            if (releaseStatusEnum.getStatus() == status){
                return releaseStatusEnum.getName();
            }
        }
        return null;
    }

    public static boolean contains(int status) {
        for (ArticleReleaseStatusEnum releaseStatusEnum: ArticleReleaseStatusEnum.values()){
            if (releaseStatusEnum.getStatus() == status){
                return true;
            }
        }
        return false;
    }

}
