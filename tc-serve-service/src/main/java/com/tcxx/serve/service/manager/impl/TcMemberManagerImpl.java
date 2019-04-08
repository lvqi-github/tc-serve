package com.tcxx.serve.service.manager.impl;

import com.tcxx.serve.service.entity.TcMember;
import com.tcxx.serve.service.manager.TcMemberManager;
import com.tcxx.serve.service.mapper.TcMemberMapper;
import com.tcxx.serve.service.query.TcMemberQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcMemberManagerImpl implements TcMemberManager {

    @Autowired
    private TcMemberMapper tcMemberMapper;

    @Override
    public boolean insert(TcMember tcMember) {
        return 1 == tcMemberMapper.insert(tcMember);
    }

    @Override
    public boolean updateMemberEndDate(TcMember tcMember) {
        return 1 == tcMemberMapper.updateMemberEndDate(tcMember);
    }

    @Override
    public TcMember getByUserId(String userId) {
        TcMemberQuery query = new TcMemberQuery();
        query.setUserId(userId);
        return tcMemberMapper.getByUserId(query);
    }

    @Override
    public List<TcMember> listByCondition(TcMemberQuery query) {
        return tcMemberMapper.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcMemberQuery query) {
        return tcMemberMapper.countByCondition(query);
    }
}
