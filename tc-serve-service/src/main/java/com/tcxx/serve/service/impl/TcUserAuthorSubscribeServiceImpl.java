package com.tcxx.serve.service.impl;

import com.tcxx.serve.service.TcUserAuthorSubscribeService;
import com.tcxx.serve.service.entity.TcUserAuthorSubscribe;
import com.tcxx.serve.service.manager.TcUserAuthorSubscribeManager;
import com.tcxx.serve.service.query.TcUserAuthorSubscribeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcUserAuthorSubscribeServiceImpl implements TcUserAuthorSubscribeService {

    @Autowired
    private TcUserAuthorSubscribeManager tcUserAuthorSubscribeManager;

    @Override
    public boolean insert(TcUserAuthorSubscribe tcUserAuthorSubscribe) {
        return tcUserAuthorSubscribeManager.insert(tcUserAuthorSubscribe);
    }

    @Override
    public void deleteByUserId(String userId) {
        tcUserAuthorSubscribeManager.deleteByUserId(userId);
    }

    @Override
    public void deleteByUserIdAndAuthorId(String userId, String authorId) {
        tcUserAuthorSubscribeManager.deleteByUserIdAndAuthorId(userId, authorId);
    }

    @Override
    public TcUserAuthorSubscribe getByUserIdAndAuthorId(String userId, String authorId) {
        return tcUserAuthorSubscribeManager.getByUserIdAndAuthorId(userId, authorId);
    }

    @Override
    public List<TcUserAuthorSubscribe> listAllByCondition(TcUserAuthorSubscribeQuery query) {
        return tcUserAuthorSubscribeManager.listAllByCondition(query);
    }

    @Override
    public List<TcUserAuthorSubscribe> listByCondition(TcUserAuthorSubscribeQuery query) {
        return tcUserAuthorSubscribeManager.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcUserAuthorSubscribeQuery query) {
        return tcUserAuthorSubscribeManager.countByCondition(query);
    }
}
