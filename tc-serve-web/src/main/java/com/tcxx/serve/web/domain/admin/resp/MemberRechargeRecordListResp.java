package com.tcxx.serve.web.domain.admin.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MemberRechargeRecordListResp {

    /** 充值记录号 **/
    private Long rechargeRecordNo;

    /** 充值套餐id **/
    private String rechargePackageId;

    /** 充值套餐描述 **/
    private String rechargePackageDesc;

    /** 充值金额 **/
    private BigDecimal rechargeAmount;

    /** 有效期天数 **/
    private Integer daysOfValidity;

    /** 有效开始时间 **/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date validityStartDate;

    /** 有效结束时间 **/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date validityEndDate;

    /** 用户id **/
    private String userId;

    /** 记录有效状态 1-有效 2-未生效 **/
    private Integer recordValidStatus;

    /** 记录有效状态 **/
    private String recordValidStatusName;

    /** 订单号 **/
    private Long orderNo;

    /** 修改时间 **/
    private Date modified;

    /** 创建时间 **/
    private Date created;
}
