package com.tcxx.serve.service.manager;

import com.tcxx.serve.service.entity.TcMemberRechargeRecord;
import com.tcxx.serve.service.query.TcMemberRechargeRecordQuery;

import java.util.List;

public interface TcMemberRechargeRecordManager {

    boolean insert(TcMemberRechargeRecord tcMemberRechargeRecord);

    TcMemberRechargeRecord getByOrderNo(Long orderNo);

    List<TcMemberRechargeRecord> listByCondition(TcMemberRechargeRecordQuery query);

    Integer countByCondition(TcMemberRechargeRecordQuery query);

}
