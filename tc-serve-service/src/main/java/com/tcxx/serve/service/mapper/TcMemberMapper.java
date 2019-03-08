package com.tcxx.serve.service.mapper;

import com.tcxx.serve.service.entity.TcMember;
import com.tcxx.serve.service.query.TcMemberQuery;

import java.util.List;

public interface TcMemberMapper {

    int insert(TcMember tcMember);

    int updateMemberEndDate(TcMember tcMember);

    TcMember getByUserId(TcMemberQuery query);

    List<TcMember> listByCondition(TcMemberQuery query);

    Integer countByCondition(TcMemberQuery query);

}
