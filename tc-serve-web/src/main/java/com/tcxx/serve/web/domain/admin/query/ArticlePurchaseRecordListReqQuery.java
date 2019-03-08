package com.tcxx.serve.web.domain.admin.query;

import lombok.Data;

@Data
public class ArticlePurchaseRecordListReqQuery {

    /** 购买记录号 **/
    private String purchaseRecordNo;

    /** 用户id **/
    private String userId;

}
