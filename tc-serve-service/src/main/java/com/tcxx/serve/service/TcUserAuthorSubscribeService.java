package com.tcxx.serve.service;

import com.tcxx.serve.service.entity.TcUserAuthorSubscribe;
import com.tcxx.serve.service.query.TcUserAuthorSubscribeQuery;

import java.util.List;

public interface TcUserAuthorSubscribeService {

    void deleteByUserId(String userId);

    List<TcUserAuthorSubscribe> listByCondition(TcUserAuthorSubscribeQuery query);

    Integer countByCondition(TcUserAuthorSubscribeQuery query);

}
