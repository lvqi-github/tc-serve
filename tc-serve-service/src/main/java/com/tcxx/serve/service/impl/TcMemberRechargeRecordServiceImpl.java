package com.tcxx.serve.service.impl;

import com.tcxx.serve.service.TcMemberRechargeRecordService;
import com.tcxx.serve.service.entity.TcMemberRechargeRecord;
import com.tcxx.serve.service.manager.TcMemberRechargeRecordManager;
import com.tcxx.serve.service.query.TcMemberRechargeRecordQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcMemberRechargeRecordServiceImpl implements TcMemberRechargeRecordService {

    @Autowired
    private TcMemberRechargeRecordManager tcMemberRechargeRecordManager;

    @Override
    public List<TcMemberRechargeRecord> listByCondition(TcMemberRechargeRecordQuery query) {
        return tcMemberRechargeRecordManager.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcMemberRechargeRecordQuery query) {
        return tcMemberRechargeRecordManager.countByCondition(query);
    }
}
