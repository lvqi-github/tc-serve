package com.tcxx.serve.service.manager;

import com.tcxx.serve.service.entity.TcUserAuthorSubscribe;
import com.tcxx.serve.service.query.TcUserAuthorSubscribeQuery;

import java.util.List;

public interface TcUserAuthorSubscribeManager {

    void deleteByUserId(String userId);

    List<TcUserAuthorSubscribe> listAllByCondition(TcUserAuthorSubscribeQuery query);

    List<TcUserAuthorSubscribe> listByCondition(TcUserAuthorSubscribeQuery query);

    Integer countByCondition(TcUserAuthorSubscribeQuery query);

}
