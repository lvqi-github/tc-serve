package com.tcxx.serve.service.manager.impl;

import com.tcxx.serve.service.entity.TcArticlePurchaseRecord;
import com.tcxx.serve.service.enumtype.ValidTypeEnum;
import com.tcxx.serve.service.manager.TcArticlePurchaseRecordManager;
import com.tcxx.serve.service.mapper.TcArticlePurchaseRecordMapper;
import com.tcxx.serve.service.query.TcArticlePurchaseRecordQuery;
import com.tcxx.serve.uid.UidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcArticlePurchaseRecordManagerImpl implements TcArticlePurchaseRecordManager {

    @Autowired
    private TcArticlePurchaseRecordMapper tcArticlePurchaseRecordMapper;

    @Autowired
    private UidGenerator cachedUidGenerator;

    @Override
    public boolean insert(TcArticlePurchaseRecord tcArticlePurchaseRecord) {
        Long purchaseRecordNo = cachedUidGenerator.getUID();
        tcArticlePurchaseRecord.setPurchaseRecordNo(purchaseRecordNo);
        tcArticlePurchaseRecord.setRecordValidStatus(ValidTypeEnum.VALID.getType());
        int i = tcArticlePurchaseRecordMapper.insert(tcArticlePurchaseRecord);
        return 1 == i;
    }

    @Override
    public TcArticlePurchaseRecord getByOrderNo(Long orderNo) {
        TcArticlePurchaseRecordQuery query = new TcArticlePurchaseRecordQuery();
        query.setOrderNo(orderNo);
        return tcArticlePurchaseRecordMapper.getByOrderNo(query);
    }

    @Override
    public List<TcArticlePurchaseRecord> listByCondition(TcArticlePurchaseRecordQuery query) {
        return tcArticlePurchaseRecordMapper.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcArticlePurchaseRecordQuery query) {
        return tcArticlePurchaseRecordMapper.countByCondition(query);
    }
}
