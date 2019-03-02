package com.tcxx.serve.service.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

@Getter
@AllArgsConstructor
public enum AuthorPlatformSourceEnum {

    QIU_TAN("P0001", "球探"),
    ZU_QIU_CAI_FU("P0002", "足球财富"),
    TIAN_TIAN_YING_QIU("P0003", "天天盈球"),
    JING_CAI_MAO("P0004", "竞彩猫"),
    QIU_QIU_SHI_DAO("P0005", "球球是道"),
    ZI_MEI_TI("P0006", "自媒体");

    /** 平台code */
    private String platformCode;

    /** 平台名称 */
    private String platformName;

    public static String getByPlatformCode(String platformCode) {
        if (StringUtils.isNotBlank(platformCode)){
            for (AuthorPlatformSourceEnum platformSourceEnum: AuthorPlatformSourceEnum.values()){
                if (platformSourceEnum.getPlatformCode().equals(platformCode)){
                    return platformSourceEnum.getPlatformName();
                }
            }
        }
        return null;
    }

    public static boolean contains(String platformCode) {
        if (StringUtils.isNotBlank(platformCode)){
            for (AuthorPlatformSourceEnum platformSourceEnum: AuthorPlatformSourceEnum.values()){
                if (platformSourceEnum.getPlatformCode().equals(platformCode)){
                    return true;
                }
            }
        }
        return false;
    }

}
