package com.tcxx.serve.service.manager.impl;

import com.tcxx.serve.service.entity.TcHitRecord;
import com.tcxx.serve.service.manager.TcHitRecordManager;
import com.tcxx.serve.service.mapper.TcHitRecordMapper;
import com.tcxx.serve.service.query.TcHitRecordQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TcHitRecordManagerImpl implements TcHitRecordManager {

    @Autowired
    private TcHitRecordMapper tcHitRecordMapper;

    @Override
    public boolean insert(TcHitRecord tcHitRecord) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        tcHitRecord.setRecordId(uuid);
        int i = tcHitRecordMapper.insert(tcHitRecord);
        return 1 == i;
    }

    @Override
    public boolean update(TcHitRecord tcHitRecord) {
        return 1 == tcHitRecordMapper.update(tcHitRecord);
    }

    @Override
    public TcHitRecord getByRecordId(String recordId) {
        TcHitRecordQuery query = new TcHitRecordQuery();
        query.setRecordId(recordId);
        return tcHitRecordMapper.getByRecordId(query);
    }

    @Override
    public List<TcHitRecord> listByCondition(TcHitRecordQuery query) {
        return tcHitRecordMapper.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcHitRecordQuery query) {
        return tcHitRecordMapper.countByCondition(query);
    }
}
