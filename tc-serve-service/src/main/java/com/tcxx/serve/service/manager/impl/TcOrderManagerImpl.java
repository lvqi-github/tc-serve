package com.tcxx.serve.service.manager.impl;

import com.tcxx.serve.core.exception.DataBaseOperateRuntimeException;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.entity.TcOrder;
import com.tcxx.serve.service.enumtype.OrderPayStatusEnum;
import com.tcxx.serve.service.manager.TcOrderManager;
import com.tcxx.serve.service.mapper.TcOrderMapper;
import com.tcxx.serve.service.query.TcOrderQuery;
import com.tcxx.serve.uid.UidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TcOrderManagerImpl implements TcOrderManager {

    @Autowired
    private TcOrderMapper tcOrderMapper;

    @Autowired
    private UidGenerator cachedUidGenerator;

    @Override
    public Long insert(TcOrder tcOrder) {
        Long orderNo = cachedUidGenerator.getUID();
        tcOrder.setOrderNo(orderNo);
        tcOrder.setOrderPayStatus(OrderPayStatusEnum.DID_NOT_PAY.getStatus());
        int i = tcOrderMapper.insert(tcOrder);
        if(i != 1){
            throw new DataBaseOperateRuntimeException(ResultCodeEnum.ERROR2501);
        }
        return orderNo;
    }

    @Override
    public boolean updateOrderPayFinished(Long orderNo, String thirdPartyPayWaterNo, Date payFinishTime) {
        TcOrder tcOrder = new TcOrder();
        tcOrder.setOrderNo(orderNo);
        tcOrder.setThirdPartyPayWaterNo(thirdPartyPayWaterNo);
        tcOrder.setPayFinishTime(payFinishTime);
        return 1 == tcOrderMapper.updateOrderPayFinished(tcOrder);
    }

    @Override
    public boolean updateOrderPayFail(Long orderNo) {
        TcOrder tcOrder = new TcOrder();
        tcOrder.setOrderNo(orderNo);
        return 1 == tcOrderMapper.updateOrderPayFail(tcOrder);
    }

    @Override
    public boolean updateOrderPayClosed(Long orderNo) {
        TcOrder tcOrder = new TcOrder();
        tcOrder.setOrderNo(orderNo);
        return 1 == tcOrderMapper.updateOrderPayClosed(tcOrder);
    }

    @Override
    public boolean updateOrderPayCancel(Long orderNo) {
        TcOrder tcOrder = new TcOrder();
        tcOrder.setOrderNo(orderNo);
        return 1 == tcOrderMapper.updateOrderPayCancel(tcOrder);
    }

    @Override
    public TcOrder getByOrderNo(Long orderNo) {
        TcOrderQuery query = new TcOrderQuery();
        query.setOrderNo(orderNo);
        return tcOrderMapper.getByOrderNo(query);
    }

    @Override
    public List<TcOrder> listNotPayOrder(Integer pageSize) {
        TcOrderQuery query = new TcOrderQuery();
        query.setPagingPageSize(pageSize);
        return tcOrderMapper.listNotPayOrder(query);
    }

    @Override
    public List<TcOrder> listByCondition(TcOrderQuery query) {
        return tcOrderMapper.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcOrderQuery query) {
        return tcOrderMapper.countByCondition(query);
    }
}
