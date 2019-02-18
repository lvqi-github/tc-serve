package com.tcxx.serve.service.mapper;

import com.tcxx.serve.service.entity.TcUser;
import com.tcxx.serve.service.query.TcUserQuery;

public interface TcUserMapper {

    int insert(TcUser tcUser);

    TcUser getByUserId(TcUserQuery query);

    TcUser getByOpenId(TcUserQuery query);

    int update(TcUser user);
}
