package com.tcxx.serve.service.impl;

import com.tcxx.serve.service.TcArticlePurchaseRecordService;
import com.tcxx.serve.service.entity.TcArticlePurchaseRecord;
import com.tcxx.serve.service.manager.TcArticlePurchaseRecordManager;
import com.tcxx.serve.service.query.TcArticlePurchaseRecordQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcArticlePurchaseRecordServiceImpl implements TcArticlePurchaseRecordService {

    @Autowired
    private TcArticlePurchaseRecordManager tcArticlePurchaseRecordManager;

    @Override
    public List<TcArticlePurchaseRecord> listByCondition(TcArticlePurchaseRecordQuery query) {
        return tcArticlePurchaseRecordManager.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcArticlePurchaseRecordQuery query) {
        return tcArticlePurchaseRecordManager.countByCondition(query);
    }
}
