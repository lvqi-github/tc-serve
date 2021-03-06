package com.tcxx.serve.service.mapper;

import com.tcxx.serve.service.entity.TcMemberRechargeRecord;
import com.tcxx.serve.service.query.TcMemberRechargeRecordQuery;

import java.util.List;

public interface TcMemberRechargeRecordMapper {

    int insert(TcMemberRechargeRecord tcMemberRechargeRecord);

    TcMemberRechargeRecord getByOrderNo(TcMemberRechargeRecordQuery query);

    List<TcMemberRechargeRecord> listByCondition(TcMemberRechargeRecordQuery query);

    Integer countByCondition(TcMemberRechargeRecordQuery query);

}
