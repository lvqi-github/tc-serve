package com.tcxx.serve.service.impl;

import com.tcxx.serve.service.TcUserService;
import com.tcxx.serve.service.entity.TcUser;
import com.tcxx.serve.service.manager.TcUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public boolean updateAccessTokenAndRefreshToken(String userId, String accessToken, String refreshToken) {
        return tcUserManager.updateAccessTokenAndRefreshToken(userId, accessToken, refreshToken);
    }
}
