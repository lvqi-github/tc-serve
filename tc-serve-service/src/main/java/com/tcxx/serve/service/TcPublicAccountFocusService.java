package com.tcxx.serve.service;

import com.tcxx.serve.service.entity.TcPublicAccountFocus;

public interface TcPublicAccountFocusService {

    boolean insert(TcPublicAccountFocus tcPublicAccountFocus);

    boolean deleteByUuid(String uuid);

    TcPublicAccountFocus getByUuid(String uuid);

}
