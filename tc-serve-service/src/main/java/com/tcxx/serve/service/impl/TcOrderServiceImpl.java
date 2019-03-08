package com.tcxx.serve.service.impl;

import com.tcxx.serve.service.TcOrderService;
import com.tcxx.serve.service.entity.TcOrder;
import com.tcxx.serve.service.manager.TcOrderManager;
import com.tcxx.serve.service.query.TcOrderQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcOrderServiceImpl implements TcOrderService {

    @Autowired
    private TcOrderManager tcOrderManager;

    @Override
    public List<TcOrder> listByCondition(TcOrderQuery query) {
        return tcOrderManager.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcOrderQuery query) {
        return tcOrderManager.countByCondition(query);
    }
}
