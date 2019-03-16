package com.tcxx.serve.service.manager.impl;

import com.tcxx.serve.service.entity.TcUserAuthorSubscribe;
import com.tcxx.serve.service.manager.TcUserAuthorSubscribeManager;
import com.tcxx.serve.service.mapper.TcUserAuthorSubscribeMapper;
import com.tcxx.serve.service.query.TcUserAuthorSubscribeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcUserAuthorSubscribeManagerImpl implements TcUserAuthorSubscribeManager {

    @Autowired
    private TcUserAuthorSubscribeMapper tcUserAuthorSubscribeMapper;

    @Override
    public void deleteByUserId(String userId) {
        TcUserAuthorSubscribe userAuthorSubscribe = new TcUserAuthorSubscribe();
        userAuthorSubscribe.setUserId(userId);
        tcUserAuthorSubscribeMapper.deleteByUserId(userAuthorSubscribe);
    }

    @Override
    public List<TcUserAuthorSubscribe> listAllByCondition(TcUserAuthorSubscribeQuery query) {
        return tcUserAuthorSubscribeMapper.listAllByCondition(query);
    }

    @Override
    public List<TcUserAuthorSubscribe> listByCondition(TcUserAuthorSubscribeQuery query) {
        return tcUserAuthorSubscribeMapper.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcUserAuthorSubscribeQuery query) {
        return tcUserAuthorSubscribeMapper.countByCondition(query);
    }
}
