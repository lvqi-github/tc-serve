package com.tcxx.serve.service.impl;

import com.tcxx.serve.service.TcPublicAccountFocusService;
import com.tcxx.serve.service.entity.TcPublicAccountFocus;
import com.tcxx.serve.service.manager.TcPublicAccountFocusManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TcPublicAccountFocusServiceImpl implements TcPublicAccountFocusService {

    @Autowired
    private TcPublicAccountFocusManager tcPublicAccountFocusManager;

    @Override
    public boolean insert(TcPublicAccountFocus tcPublicAccountFocus) {
        return tcPublicAccountFocusManager.insert(tcPublicAccountFocus);
    }

    @Override
    public boolean deleteByUuid(String uuid) {
        return tcPublicAccountFocusManager.deleteByUuid(uuid);
    }

    @Override
    public TcPublicAccountFocus getByUuid(String uuid) {
        return tcPublicAccountFocusManager.getByUuid(uuid);
    }
}
