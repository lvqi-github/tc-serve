package com.tcxx.serve.service.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  OrderPayChannelTypeEnum {

    WECHAT(1, "微信支付");

    /** 类型 */
    private int type;

    /** 名称 */
    private String name;

    public static String getByType(int type) {
        for (OrderPayChannelTypeEnum payChannelTypeEnum: OrderPayChannelTypeEnum.values()){
            if (payChannelTypeEnum.getType() == type){
                return payChannelTypeEnum.getName();
            }
        }
        return null;
    }

    public static boolean contains(int type) {
        for (OrderPayChannelTypeEnum payChannelTypeEnum: OrderPayChannelTypeEnum.values()){
            if (payChannelTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }

}
