package com.tcxx.serve.service.impl;

import com.tcxx.serve.service.TcMemberService;
import com.tcxx.serve.service.entity.TcMember;
import com.tcxx.serve.service.manager.TcMemberManager;
import com.tcxx.serve.service.query.TcMemberQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcMemberServiceImpl implements TcMemberService {

    @Autowired
    private TcMemberManager tcMemberManager;

    @Override
    public List<TcMember> listByCondition(TcMemberQuery query) {
        return tcMemberManager.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcMemberQuery query) {
        return tcMemberManager.countByCondition(query);
    }
}
