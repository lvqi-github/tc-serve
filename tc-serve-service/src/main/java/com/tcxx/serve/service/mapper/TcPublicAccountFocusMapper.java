package com.tcxx.serve.service.mapper;

import com.tcxx.serve.service.entity.TcPublicAccountFocus;
import com.tcxx.serve.service.query.TcPublicAccountFocusQuery;

public interface TcPublicAccountFocusMapper {

    int insert(TcPublicAccountFocus tcPublicAccountFocus);

    int deleteByUuid(TcPublicAccountFocus tcPublicAccountFocus);

    TcPublicAccountFocus getByUuid(TcPublicAccountFocusQuery query);

}
