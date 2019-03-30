package com.tcxx.serve.core.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  AppEnum {

    APP_BALL("99b8d17981574fe9a7a3a480a11cf38d", "b8683f967d8543df97ec466f8ab6c396"),
    APP_ADMIN("3f4404d68b7742ce895d7723a7a1f384", "f7c0ba8db844432eaf2d99ccd3870aa3");

    /** appId */
    private String appId;

    /** appSecret */
    private String appSecret;

    public static String getByAppId(String appId) {
        for (AppEnum appEnum: AppEnum.values()){
            if (appEnum.getAppId().equals(appId)){
                return appEnum.getAppSecret();
            }
        }
        return null;
    }

    public static boolean contains(String appId) {
        for (AppEnum appEnum: AppEnum.values()){
            if (appEnum.getAppId().equals(appId)){
                return true;
            }
        }
        return false;
    }

}
