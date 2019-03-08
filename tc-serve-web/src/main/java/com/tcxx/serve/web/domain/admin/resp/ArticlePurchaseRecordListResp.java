package com.tcxx.serve.web.domain.admin.resp;

import lombok.Data;

import java.util.Date;

@Data
public class ArticlePurchaseRecordListResp {

    /** 购买记录号 **/
    private String purchaseRecordNo;

    /** 文章id **/
    private String articleId;

    /** 文章标题 **/
    private String articleTitle;

    /** 文章描述 **/
    private String articleDesc;

    /** 文章发布时间 **/
    private String articleReleaseTime;

    /** 金额 **/
    private String amount;

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
