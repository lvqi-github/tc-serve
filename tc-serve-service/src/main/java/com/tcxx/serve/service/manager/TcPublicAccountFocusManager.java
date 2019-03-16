package com.tcxx.serve.service.manager;

import com.tcxx.serve.service.entity.TcPublicAccountFocus;

public interface TcPublicAccountFocusManager {

    boolean insert(TcPublicAccountFocus tcPublicAccountFocus);

    boolean deleteByUuid(String uuid);

    TcPublicAccountFocus getByUuid(String uuid);

}
