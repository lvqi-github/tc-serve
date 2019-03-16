package com.tcxx.serve.service.manager.impl;

import com.tcxx.serve.core.utils.BusinessUuidGenerateUtil;
import com.tcxx.serve.service.entity.TcPublicAccountFocus;
import com.tcxx.serve.service.manager.TcPublicAccountFocusManager;
import com.tcxx.serve.service.mapper.TcPublicAccountFocusMapper;
import com.tcxx.serve.service.query.TcPublicAccountFocusQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TcPublicAccountFocusManagerImpl implements TcPublicAccountFocusManager {

    @Autowired
    private TcPublicAccountFocusMapper tcPublicAccountFocusMapper;

    @Override
    public boolean insert(TcPublicAccountFocus tcPublicAccountFocus) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        tcPublicAccountFocus.setId(uuid);
        tcPublicAccountFocus.setUuid(BusinessUuidGenerateUtil.getTcPublicAccountFocusUuid(tcPublicAccountFocus.getPublicAccountWechatId(),
                tcPublicAccountFocus.getOpenId()));

        int i = tcPublicAccountFocusMapper.insert(tcPublicAccountFocus);
        return 1 == i;
    }

    @Override
    public boolean deleteByUuid(String uuid) {
        TcPublicAccountFocus tcPublicAccountFocus = new TcPublicAccountFocus();
        tcPublicAccountFocus.setUuid(uuid);
        return 0 < tcPublicAccountFocusMapper.deleteByUuid(tcPublicAccountFocus);
    }

    @Override
    public TcPublicAccountFocus getByUuid(String uuid) {
        TcPublicAccountFocusQuery query = new TcPublicAccountFocusQuery();
        query.setUuid(uuid);
        return tcPublicAccountFocusMapper.getByUuid(query);
    }
}
