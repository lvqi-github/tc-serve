package com.tcxx.serve.service.query;

import lombok.Data;

@Data
public class TcOrderQuery extends Pagination {

    /** 订单号 **/
    private Long orderNo;

    /** 订单类型 1-会员充值订单 2-文章购买订单 **/
    private Integer orderType;

    /** 订单支付状态 1-未支付 2-支付完成 99-已作废 **/
    private Integer orderPayStatus;

    /** 用户id **/
    private String userId;

}
