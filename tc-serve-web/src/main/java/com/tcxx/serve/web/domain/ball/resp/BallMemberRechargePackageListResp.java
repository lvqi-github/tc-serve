package com.tcxx.serve.web.domain.ball.resp;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BallMemberRechargePackageListResp {

    /** 套餐id**/
    private String packageId;

    /** 套餐描述 **/
    private String packageDesc;

    /** 价格(元) **/
    private BigDecimal price;

    /** 价格(分) **/
    private Long centPrice;

    /** 每日价格(元) **/
    private BigDecimal dailyPrice;

    /** 是否超值套餐 **/
    private Boolean specialCombo;

}
