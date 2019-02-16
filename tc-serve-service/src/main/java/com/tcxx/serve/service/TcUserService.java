package com.tcxx.serve.service;

import com.tcxx.serve.service.entity.TcUser;

public interface TcUserService {

    /**
     * 插入
     * @param tcUser
     * @return 主键
     */
    String insert(TcUser tcUser);

    TcUser getByUserId(String userId);

    boolean updateAccessTokenAndRefreshToken(String userId, String accessToken, String refreshToken);

}