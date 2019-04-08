package com.tcxx.serve.service.manager.impl;

import com.tcxx.serve.service.entity.TcMemberRechargeRecord;
import com.tcxx.serve.service.enumtype.ValidTypeEnum;
import com.tcxx.serve.service.manager.TcMemberRechargeRecordManager;
import com.tcxx.serve.service.mapper.TcMemberRechargeRecordMapper;
import com.tcxx.serve.service.query.TcMemberRechargeRecordQuery;
import com.tcxx.serve.uid.UidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcMemberRechargeRecordManagerImpl implements TcMemberRechargeRecordManager {

    @Autowired
    private TcMemberRechargeRecordMapper tcMemberRechargeRecordMapper;

    @Autowired
    private UidGenerator cachedUidGenerator;

    @Override
    public boolean insert(TcMemberRechargeRecord tcMemberRechargeRecord) {
        Long rechargeRecordNo = cachedUidGenerator.getUID();
        tcMemberRechargeRecord.setRechargeRecordNo(rechargeRecordNo);
        tcMemberRechargeRecord.setRecordValidStatus(ValidTypeEnum.VALID.getType());
        int i = tcMemberRechargeRecordMapper.insert(tcMemberRechargeRecord);
        return 1 == i;
    }

    @Override
    public TcMemberRechargeRecord getByOrderNo(Long orderNo) {
        TcMemberRechargeRecordQuery query = new TcMemberRechargeRecordQuery();
        query.setOrderNo(orderNo);
        return tcMemberRechargeRecordMapper.getByOrderNo(query);
    }

    @Override
    public List<TcMemberRechargeRecord> listByCondition(TcMemberRechargeRecordQuery query) {
        return tcMemberRechargeRecordMapper.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcMemberRechargeRecordQuery query) {
        return tcMemberRechargeRecordMapper.countByCondition(query);
    }
}
