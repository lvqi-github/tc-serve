package com.tcxx.serve.service.mapper;

import com.tcxx.serve.service.entity.TcHitRecord;
import com.tcxx.serve.service.query.TcHitRecordQuery;

import java.util.List;

public interface TcHitRecordMapper {

    int insert(TcHitRecord tcHitRecord);

    int update(TcHitRecord tcHitRecord);

    TcHitRecord getByRecordId(TcHitRecordQuery query);

    List<TcHitRecord> listByCondition(TcHitRecordQuery query);

    Integer countByCondition(TcHitRecordQuery query);

}
