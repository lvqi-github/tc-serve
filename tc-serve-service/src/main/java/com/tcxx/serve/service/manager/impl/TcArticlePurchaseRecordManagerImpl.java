package com.tcxx.serve.service.manager.impl;

import com.tcxx.serve.service.entity.TcArticlePurchaseRecord;
import com.tcxx.serve.service.manager.TcArticlePurchaseRecordManager;
import com.tcxx.serve.service.mapper.TcArticlePurchaseRecordMapper;
import com.tcxx.serve.service.query.TcArticlePurchaseRecordQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcArticlePurchaseRecordManagerImpl implements TcArticlePurchaseRecordManager {

    @Autowired
    private TcArticlePurchaseRecordMapper tcArticlePurchaseRecordMapper;

    @Override
    public List<TcArticlePurchaseRecord> listByCondition(TcArticlePurchaseRecordQuery query) {
        return tcArticlePurchaseRecordMapper.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcArticlePurchaseRecordQuery query) {
        return tcArticlePurchaseRecordMapper.countByCondition(query);
    }
}
