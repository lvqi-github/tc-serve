package com.tcxx.serve.service.manager.impl;

import com.tcxx.serve.service.entity.TcMemberRechargeRecord;
import com.tcxx.serve.service.manager.TcMemberRechargeRecordManager;
import com.tcxx.serve.service.mapper.TcMemberRechargeRecordMapper;
import com.tcxx.serve.service.query.TcMemberRechargeRecordQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcMemberRechargeRecordManagerImpl implements TcMemberRechargeRecordManager {

    @Autowired
    private TcMemberRechargeRecordMapper tcMemberRechargeRecordMapper;

    @Override
    public List<TcMemberRechargeRecord> listByCondition(TcMemberRechargeRecordQuery query) {
        return tcMemberRechargeRecordMapper.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcMemberRechargeRecordQuery query) {
        return tcMemberRechargeRecordMapper.countByCondition(query);
    }
}
