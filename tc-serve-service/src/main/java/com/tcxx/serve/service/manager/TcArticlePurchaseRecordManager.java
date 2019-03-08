package com.tcxx.serve.service.manager;

import com.tcxx.serve.service.entity.TcArticlePurchaseRecord;
import com.tcxx.serve.service.query.TcArticlePurchaseRecordQuery;

import java.util.List;

public interface TcArticlePurchaseRecordManager {

    List<TcArticlePurchaseRecord> listByCondition(TcArticlePurchaseRecordQuery query);

    Integer countByCondition(TcArticlePurchaseRecordQuery query);

}
