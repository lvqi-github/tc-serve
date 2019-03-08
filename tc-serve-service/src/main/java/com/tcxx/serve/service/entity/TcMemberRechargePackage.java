package com.tcxx.serve.service.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TcMemberRechargePackage {

    /** 套餐id**/
    private String packageId;

    /** 套餐描述 **/
    private String packageDesc;

    /** 有效期天数 **/
    private Integer daysOfValidity;

    /** 价格 **/
    private BigDecimal price;

    /** 启用状态 1-启用 2-不启用 **/
    private Integer enableStatus;

    /** 修改时间 **/
    private Date modified;

    /** 创建时间 **/
    private Date created;
}
