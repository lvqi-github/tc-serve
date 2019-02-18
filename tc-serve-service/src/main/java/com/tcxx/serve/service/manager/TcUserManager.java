package com.tcxx.serve.service.manager;

import com.tcxx.serve.service.entity.TcUser;

public interface TcUserManager {

    /**
     * 插入
     * @param tcUser
     * @return 主键
     */
    String insert(TcUser tcUser);

    TcUser getByUserId(String userId);

    TcUser getByOpenId(String openId);

    boolean update(TcUser user);

}
