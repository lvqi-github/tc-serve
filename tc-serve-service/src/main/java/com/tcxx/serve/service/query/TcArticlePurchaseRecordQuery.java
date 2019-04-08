package com.tcxx.serve.service.query;

import lombok.Data;

@Data
public class TcArticlePurchaseRecordQuery extends Pagination {

    /** 购买记录号 **/
    private String purchaseRecordNo;

    /** 订单号 **/
    private Long orderNo;

    /** 用户id **/
    private String userId;

    /** 文章id **/
    private String articleId;

    /** 记录有效状态 1-有效 2-未生效 **/
    private Integer recordValidStatus;

}
