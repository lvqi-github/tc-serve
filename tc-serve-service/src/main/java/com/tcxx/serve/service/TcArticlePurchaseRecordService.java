package com.tcxx.serve.service;

import com.tcxx.serve.service.entity.TcArticlePurchaseRecord;
import com.tcxx.serve.service.entity.TcOrder;
import com.tcxx.serve.service.query.TcArticlePurchaseRecordQuery;

import java.util.Date;
import java.util.List;

public interface TcArticlePurchaseRecordService {

    void handlerArticlePurchase(TcOrder tcOrder, String transactionId, Date payFinishedTime);

    List<TcArticlePurchaseRecord> listByCondition(TcArticlePurchaseRecordQuery query);

    Integer countByCondition(TcArticlePurchaseRecordQuery query);

}
