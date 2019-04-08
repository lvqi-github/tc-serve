package com.tcxx.serve.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tcxx.serve.core.exception.DataBaseOperateRuntimeException;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.TcMemberRechargeRecordService;
import com.tcxx.serve.service.entity.TcMember;
import com.tcxx.serve.service.entity.TcMemberRechargePackage;
import com.tcxx.serve.service.entity.TcMemberRechargeRecord;
import com.tcxx.serve.service.entity.TcOrder;
import com.tcxx.serve.service.manager.TcMemberManager;
import com.tcxx.serve.service.manager.TcMemberRechargePackageManager;
import com.tcxx.serve.service.manager.TcMemberRechargeRecordManager;
import com.tcxx.serve.service.manager.TcOrderManager;
import com.tcxx.serve.service.query.TcMemberRechargeRecordQuery;
import com.tcxx.serve.uid.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TcMemberRechargeRecordServiceImpl implements TcMemberRechargeRecordService {

    @Autowired
    private TcMemberManager tcMemberManager;

    @Autowired
    private TcMemberRechargePackageManager tcMemberRechargePackageManager;

    @Autowired
    private TcMemberRechargeRecordManager tcMemberRechargeRecordManager;

    @Autowired
    private TcOrderManager tcOrderManager;

    @Override
    @Transactional
    public void handlerMemberRecharge(TcOrder tcOrder, String transactionId, Date payFinishedTime) {
        // 购买的会员套餐信息
        JSONObject jsonObject = JSONObject.parseObject(tcOrder.getBusinessData());
        TcMemberRechargePackage rechargePackage = tcMemberRechargePackageManager.getByPackageId(jsonObject.getString("rechargePackageId"));

        Date validityStartTime; //有效期开始时间
        Date validityEndTime; //有效期结束时间

        // 增加会员有效期
        TcMember tcMember = tcMemberManager.getByUserId(tcOrder.getUserId());
        if (Objects.isNull(tcMember)){// 新增会员
            validityStartTime = payFinishedTime;
            validityEndTime = DateUtils.addDays(validityStartTime, rechargePackage.getDaysOfValidity());

            tcMember = new TcMember();
            tcMember.setUserId(tcOrder.getUserId());
            tcMember.setFirstMemberOpenTime(payFinishedTime); //首次开通时间为支付完成时间
            tcMember.setMemberEndDate(validityEndTime);
            boolean insert = tcMemberManager.insert(tcMember);
            if (!insert){
                throw new DataBaseOperateRuntimeException(ResultCodeEnum.ERROR2501);
            }
        }else {// 修改会员有效期
            Date currentTime = new Date();
            if (currentTime.after(tcMember.getMemberEndDate())) { //当前时间 在 会员结束时间 之后 代表已失效 使用支付完成时间 作为初始时间
                validityStartTime = payFinishedTime;
                validityEndTime = DateUtils.addDays(validityStartTime, rechargePackage.getDaysOfValidity());
            }else { //有效期内购买为续费 使用会员结束日期 作为初始时间
                validityStartTime = tcMember.getMemberEndDate();
                validityEndTime = DateUtils.addDays(validityStartTime, rechargePackage.getDaysOfValidity());
            }
            tcMember.setMemberEndDate(validityEndTime);
            boolean b = tcMemberManager.updateMemberEndDate(tcMember);
            if (!b){
                throw new DataBaseOperateRuntimeException(ResultCodeEnum.ERROR2501);
            }
        }

        // 写入会员充值记录
        TcMemberRechargeRecord rechargeRecord = new TcMemberRechargeRecord();
        rechargeRecord.setRechargePackageId(rechargePackage.getPackageId());
        rechargeRecord.setRechargePackageDesc(rechargePackage.getPackageDesc());
        rechargeRecord.setRechargeAmount(tcOrder.getOrderAmount());
        rechargeRecord.setDaysOfValidity(rechargePackage.getDaysOfValidity());
        rechargeRecord.setValidityStartDate(validityStartTime);
        rechargeRecord.setValidityEndDate(validityEndTime);
        rechargeRecord.setUserId(tcOrder.getUserId());
        rechargeRecord.setOrderNo(tcOrder.getOrderNo());
        boolean insert = tcMemberRechargeRecordManager.insert(rechargeRecord);
        if (!insert){
            throw new DataBaseOperateRuntimeException(ResultCodeEnum.ERROR2501);
        }

        // 更改订单状态
        boolean b = tcOrderManager.updateOrderPayFinished(tcOrder.getOrderNo(), transactionId, payFinishedTime);
        if (!b){
            throw new DataBaseOperateRuntimeException(ResultCodeEnum.ERROR2501);
        }
    }

    @Override
    public List<TcMemberRechargeRecord> listByCondition(TcMemberRechargeRecordQuery query) {
        return tcMemberRechargeRecordManager.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcMemberRechargeRecordQuery query) {
        return tcMemberRechargeRecordManager.countByCondition(query);
    }
}
