package com.tcxx.serve.service.manager.impl;

import com.tcxx.serve.service.entity.TcOrder;
import com.tcxx.serve.service.manager.TcOrderManager;
import com.tcxx.serve.service.mapper.TcOrderMapper;
import com.tcxx.serve.service.query.TcOrderQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcOrderManagerImpl implements TcOrderManager {

    @Autowired
    private TcOrderMapper tcOrderMapper;

    @Override
    public List<TcOrder> listByCondition(TcOrderQuery query) {
        return tcOrderMapper.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcOrderQuery query) {
        return tcOrderMapper.countByCondition(query);
    }
}
