package com.tcxx.serve.service.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TcMemberRechargeRecord {

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
    private Date validityStartDate;

    /** 有效结束时间 **/
    private Date validityEndDate;

    /** 用户id **/
    private String userId;

    /** 记录有效状态 1-有效 2-未生效 **/
    private Integer recordValidStatus;

    /** 订单号 **/
    private Long orderNo;

    /** 修改时间 **/
    private Date modified;

    /** 创建时间 **/
    private Date created;

}
