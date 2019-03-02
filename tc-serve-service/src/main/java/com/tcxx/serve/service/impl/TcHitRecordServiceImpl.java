package com.tcxx.serve.service.impl;

import com.tcxx.serve.service.TcHitRecordService;
import com.tcxx.serve.service.entity.TcHitRecord;
import com.tcxx.serve.service.manager.TcHitRecordManager;
import com.tcxx.serve.service.query.TcHitRecordQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcHitRecordServiceImpl implements TcHitRecordService {

    @Autowired
    private TcHitRecordManager tcHitRecordManager;

    @Override
    public boolean insert(TcHitRecord tcHitRecord) {
        return tcHitRecordManager.insert(tcHitRecord);
    }

    @Override
    public boolean update(TcHitRecord tcHitRecord) {
        return tcHitRecordManager.update(tcHitRecord);
    }

    @Override
    public TcHitRecord getByRecordId(String recordId) {
        return tcHitRecordManager.getByRecordId(recordId);
    }

    @Override
    public List<TcHitRecord> listByCondition(TcHitRecordQuery query) {
        return tcHitRecordManager.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcHitRecordQuery query) {
        return tcHitRecordManager.countByCondition(query);
    }
}
