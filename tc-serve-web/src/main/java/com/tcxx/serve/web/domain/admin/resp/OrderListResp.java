package com.tcxx.serve.web.domain.admin.resp;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderListResp {

    /** 订单号 **/
    private Long orderNo;

    /** 订单金额 **/
    private BigDecimal orderAmount;

    /** 订单类型 1-会员充值订单 2-文章购买订单 **/
    private Integer orderType;

    /** 订单类型 **/
    private String orderTypeName;

    /** 订单支付状态 1-未支付 2-支付完成 99-已作废 **/
    private Integer orderPayStatus;

    /** 订单支付状态 **/
    private String orderPayStatusName;

    /** 用户id **/
    private String userId;

    /** 支付渠道类型 1-微信支付 **/
    private Integer payChannelType;

    /** 支付渠道类型 **/
    private String payChannelTypeName;

    /** 支付完成时间 **/
    private Date payFinishTime;

    /** 第三方支付流水号 **/
    private String thirdPartyPayWaterNo;

    /** 业务数据 **/
    private String businessData;

    /** 修改时间 **/
    private Date modified;

    /** 创建时间 **/
    private Date created;

}
