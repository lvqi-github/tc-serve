package com.tcxx.serve.service.manager.impl;

import com.tcxx.serve.core.exception.DataBaseOperateRuntimeException;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.entity.TcUser;
import com.tcxx.serve.service.manager.TcUserManager;
import com.tcxx.serve.service.mapper.TcUserMapper;
import com.tcxx.serve.service.query.TcUserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TcUserManagerImpl implements TcUserManager {

    @Autowired
    private TcUserMapper tcUserMapper;

    @Override
    public String insert(TcUser tcUser) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        tcUser.setId(uuid);
        int i = tcUserMapper.insert(tcUser);
        if(i != 1){
            throw new DataBaseOperateRuntimeException(ResultCodeEnum.ERROR2501);
        }
        return uuid;
    }

    @Override
    public TcUser getByUserId(String userId) {
        TcUserQuery query = new TcUserQuery();
        query.setId(userId);
        return tcUserMapper.getByUserId(query);
    }

    @Override
    public TcUser getByOpenId(String openId) {
        TcUserQuery query = new TcUserQuery();
        query.setOpenId(openId);
        return tcUserMapper.getByOpenId(query);
    }

    @Override
    public boolean update(TcUser user) {
        return 1 == tcUserMapper.update(user);
    }
}
