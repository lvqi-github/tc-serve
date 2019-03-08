package com.tcxx.serve.web.domain.admin.query;

import lombok.Data;

@Data
public class OrderListReqQuery {

    /** 订单号 **/
    private Long orderNo;

    /** 用户id **/
    private String userId;

    /** 订单类型 1-会员充值订单 2-文章购买订单 **/
    private Integer orderType;

    /** 订单支付状态 1-未支付 2-支付完成 99-已作废 **/
    private Integer orderPayStatus;

}
