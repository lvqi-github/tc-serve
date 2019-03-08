package com.tcxx.serve.service.mapper;

import com.tcxx.serve.service.entity.TcUser;
import com.tcxx.serve.service.query.TcUserQuery;

import java.util.List;

public interface TcUserMapper {

    int insert(TcUser tcUser);

    TcUser getByUserId(TcUserQuery query);

    TcUser getByOpenId(TcUserQuery query);

    int update(TcUser user);

    List<TcUser> listByCondition(TcUserQuery query);

    Integer countByCondition(TcUserQuery query);
}
