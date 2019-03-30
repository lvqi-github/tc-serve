package com.tcxx.serve.web.domain.ball.resp;

import lombok.Data;

import java.util.Date;

@Data
public class BallArticlePurchaseRecordListResp {

    /** 购买记录号 **/
    private String purchaseRecordNo;

    /** 文章标题 **/
    private String articleTitle;

    /** 文章描述 **/
    private String articleDesc;

    /** 金额 **/
    private String amount;

    /** 订单号 **/
    private Long orderNo;

    /** 创建时间 **/
    private Date created;
}
