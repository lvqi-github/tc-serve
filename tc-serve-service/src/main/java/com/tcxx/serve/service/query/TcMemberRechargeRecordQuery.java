package com.tcxx.serve.service.query;

import lombok.Data;

@Data
public class TcMemberRechargeRecordQuery extends Pagination {

    /** 充值记录号 **/
    private Long rechargeRecordNo;

    /** 订单号 **/
    private Long orderNo;

    /** 用户id **/
    private String userId;

    /** 记录有效状态 1-有效 2-未生效 **/
    private Integer recordValidStatus;

}
