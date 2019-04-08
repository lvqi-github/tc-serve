package com.tcxx.serve.service;

import com.tcxx.serve.service.entity.TcMemberRechargeRecord;
import com.tcxx.serve.service.entity.TcOrder;
import com.tcxx.serve.service.query.TcMemberRechargeRecordQuery;
import com.tcxx.serve.wechat.model.pay.PayNotifyResult;

import java.util.Date;
import java.util.List;

public interface TcMemberRechargeRecordService {

    void handlerMemberRecharge(TcOrder tcOrder, String transactionId, Date payFinishedTime);

    List<TcMemberRechargeRecord> listByCondition(TcMemberRechargeRecordQuery query);

    Integer countByCondition(TcMemberRechargeRecordQuery query);

}
