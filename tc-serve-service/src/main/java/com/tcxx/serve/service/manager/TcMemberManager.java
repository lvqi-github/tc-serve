package com.tcxx.serve.service.manager;

import com.tcxx.serve.service.entity.TcMember;
import com.tcxx.serve.service.query.TcMemberQuery;

import java.util.List;

public interface TcMemberManager {

    TcMember getByUserId(String userId);

    List<TcMember> listByCondition(TcMemberQuery query);

    Integer countByCondition(TcMemberQuery query);
}
