package com.tcxx.serve.service.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  ArticlePushJobStatusEnum {

    PUSH_JOB_NOT_GENERATED(1, "未生成推送任务"),
    PUSH_JOB_GENERATED(2, "已生成推送任务");

    /** 状态 */
    private int status;

    /** 名称 */
    private String name;

    public static String getByStatus(int status) {
        for (ArticlePushJobStatusEnum pushJobStatusEnum: ArticlePushJobStatusEnum.values()){
            if (pushJobStatusEnum.getStatus() == status){
                return pushJobStatusEnum.getName();
            }
        }
        return null;
    }

    public static boolean contains(int status) {
        for (ArticlePushJobStatusEnum pushJobStatusEnum: ArticlePushJobStatusEnum.values()){
            if (pushJobStatusEnum.getStatus() == status){
                return true;
            }
        }
        return false;
    }

}
