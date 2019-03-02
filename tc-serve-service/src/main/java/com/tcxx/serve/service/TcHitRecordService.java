package com.tcxx.serve.service;

import com.tcxx.serve.service.entity.TcHitRecord;
import com.tcxx.serve.service.query.TcHitRecordQuery;

import java.util.List;

public interface TcHitRecordService {

    boolean insert(TcHitRecord tcHitRecord);

    boolean update(TcHitRecord tcHitRecord);

    TcHitRecord getByRecordId(String recordId);

    List<TcHitRecord> listByCondition(TcHitRecordQuery query);

    Integer countByCondition(TcHitRecordQuery query);

}
