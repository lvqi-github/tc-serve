package com.tcxx.serve.web.domain.ball.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BallMemberRechargeRecordListResp {

    /** 充值记录号 **/
    private Long rechargeRecordNo;

    /** 充值套餐描述 **/
    private String rechargePackageDesc;

    /** 充值金额 **/
    private BigDecimal rechargeAmount;

    /** 有效开始时间 **/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date validityStartDate;

    /** 有效结束时间 **/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date validityEndDate;

    /** 订单号 **/
    private Long orderNo;

    /** 创建时间 **/
    private Date created;
}
