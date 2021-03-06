package com.tcxx.serve.service.mapper;

import com.tcxx.serve.service.entity.TcArticlePurchaseRecord;
import com.tcxx.serve.service.query.TcArticlePurchaseRecordQuery;

import java.util.List;

public interface TcArticlePurchaseRecordMapper {

    int insert(TcArticlePurchaseRecord tcArticlePurchaseRecord);

    TcArticlePurchaseRecord getByOrderNo(TcArticlePurchaseRecordQuery query);

    List<TcArticlePurchaseRecord> listByCondition(TcArticlePurchaseRecordQuery query);

    Integer countByCondition(TcArticlePurchaseRecordQuery query);

}
