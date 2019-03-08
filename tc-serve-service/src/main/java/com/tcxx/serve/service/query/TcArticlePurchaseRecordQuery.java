package com.tcxx.serve.service.query;

import lombok.Data;

@Data
public class TcArticlePurchaseRecordQuery extends Pagination {

    /** 购买记录号 **/
    private String purchaseRecordNo;

    /** 用户id **/
    private String userId;

}
