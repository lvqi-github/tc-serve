package com.tcxx.serve.service.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderTypeEnum {

    MEMBER_RECHARGE(1, "会员充值订单"),
    ARTICLE_PURCHASE(2, "文章购买订单");

    /** 类型 */
    private int type;

    /** 名称 */
    private String name;

    public static String getByType(int type) {
        for (OrderTypeEnum orderTypeEnum: OrderTypeEnum.values()){
            if (orderTypeEnum.getType() == type){
                return orderTypeEnum.getName();
            }
        }
        return null;
    }

    public static boolean contains(int type) {
        for (OrderTypeEnum orderTypeEnum: OrderTypeEnum.values()){
            if (orderTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }

}
