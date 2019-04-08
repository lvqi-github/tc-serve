package com.tcxx.serve.service.impl;

import com.tcxx.serve.service.TcOrderService;
import com.tcxx.serve.service.entity.TcOrder;
import com.tcxx.serve.service.manager.TcOrderManager;
import com.tcxx.serve.service.query.TcOrderQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TcOrderServiceImpl implements TcOrderService {

    @Autowired
    private TcOrderManager tcOrderManager;

    @Override
    public Long insert(TcOrder tcOrder) {
        return tcOrderManager.insert(tcOrder);
    }

    @Override
    public boolean updateOrderPayFinished(Long orderNo, String thirdPartyPayWaterNo, Date payFinishTime) {
        return tcOrderManager.updateOrderPayFinished(orderNo, thirdPartyPayWaterNo, payFinishTime);
    }

    @Override
    public boolean updateOrderPayFail(Long orderNo) {
        return tcOrderManager.updateOrderPayFail(orderNo);
    }

    @Override
    public boolean updateOrderPayClosed(Long orderNo) {
        return tcOrderManager.updateOrderPayClosed(orderNo);
    }

    @Override
    public boolean updateOrderPayCancel(Long orderNo) {
        return tcOrderManager.updateOrderPayCancel(orderNo);
    }

    @Override
    public TcOrder getByOrderNo(Long orderNo) {
        return tcOrderManager.getByOrderNo(orderNo);
    }

    @Override
    public List<TcOrder> listNotPayOrder(Integer pageSize) {
        return tcOrderManager.listNotPayOrder(pageSize);
    }

    @Override
    public List<TcOrder> listByCondition(TcOrderQuery query) {
        return tcOrderManager.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcOrderQuery query) {
        return tcOrderManager.countByCondition(query);
    }
}
