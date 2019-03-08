package com.tcxx.serve.service;

import com.tcxx.serve.service.entity.TcArticlePurchaseRecord;
import com.tcxx.serve.service.query.TcArticlePurchaseRecordQuery;

import java.util.List;

public interface TcArticlePurchaseRecordService {

    List<TcArticlePurchaseRecord> listByCondition(TcArticlePurchaseRecordQuery query);

    Integer countByCondition(TcArticlePurchaseRecordQuery query);

}
