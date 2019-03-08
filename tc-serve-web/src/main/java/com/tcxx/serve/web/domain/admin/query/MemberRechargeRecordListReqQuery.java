package com.tcxx.serve.web.domain.admin.query;

import lombok.Data;

@Data
public class MemberRechargeRecordListReqQuery {

    /** 充值记录号 **/
    private Long rechargeRecordNo;

    /** 用户id **/
    private String userId;

}
