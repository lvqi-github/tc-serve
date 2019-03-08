package com.tcxx.serve.service.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderPayStatusEnum {

    DID_NOT_PAY(1, "未支付"),
    PAY_TO_COMPLETE(2, "支付完成"),
    CANCELLATION(99, "已作废");

    /** 状态 */
    private int status;

    /** 名称 */
    private String name;

    public static String getByStatus(int status) {
        for (OrderPayStatusEnum orderPayStatusEnum: OrderPayStatusEnum.values()){
            if (orderPayStatusEnum.getStatus() == status){
                return orderPayStatusEnum.getName();
            }
        }
        return null;
    }

    public static boolean contains(int status) {
        for (OrderPayStatusEnum orderPayStatusEnum: OrderPayStatusEnum.values()){
            if (orderPayStatusEnum.getStatus() == status){
                return true;
            }
        }
        return false;
    }

}
