package com.tcxx.serve.service;

import com.tcxx.serve.service.entity.TcMember;
import com.tcxx.serve.service.query.TcMemberQuery;

import java.util.List;

public interface TcMemberService {

    List<TcMember> listByCondition(TcMemberQuery query);

    Integer countByCondition(TcMemberQuery query);

}
