package com.tcxx.serve.service.impl;

import com.tcxx.serve.service.TcUserService;
import com.tcxx.serve.service.entity.TcUser;
import com.tcxx.serve.service.manager.TcUserManager;
import com.tcxx.serve.service.query.TcUserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcUserServiceImpl implements TcUserService {

    @Autowired
    private TcUserManager tcUserManager;

    @Override
    public String insert(TcUser tcUser) {
        return tcUserManager.insert(tcUser);
    }

    @Override
    public TcUser getByUserId(String userId) {
        return tcUserManager.getByUserId(userId);
    }

    @Override
    public TcUser getByOpenId(String openId) {
        return tcUserManager.getByOpenId(openId);
    }

    @Override
    public boolean update(TcUser user) {
        return tcUserManager.update(user);
    }

    @Override
    public List<TcUser> listByCondition(TcUserQuery query) {
        return tcUserManager.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcUserQuery query) {
        return tcUserManager.countByCondition(query);
    }
}
