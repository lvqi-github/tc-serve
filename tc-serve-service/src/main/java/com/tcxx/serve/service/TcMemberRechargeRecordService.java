package com.tcxx.serve.service;

import com.tcxx.serve.service.entity.TcMemberRechargeRecord;
import com.tcxx.serve.service.query.TcMemberRechargeRecordQuery;

import java.util.List;

public interface TcMemberRechargeRecordService {

    List<TcMemberRechargeRecord> listByCondition(TcMemberRechargeRecordQuery query);

    Integer countByCondition(TcMemberRechargeRecordQuery query);

}
