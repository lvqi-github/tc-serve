package com.tcxx.serve.service.query;

import lombok.Data;

@Data
public class TcMemberRechargeRecordQuery extends Pagination {

    /** 充值记录号 **/
    private Long rechargeRecordNo;

    /** 用户id **/
    private String userId;

}
