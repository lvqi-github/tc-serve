package com.tcxx.serve.service;

import com.tcxx.serve.service.entity.TcUserAuthorSubscribe;
import com.tcxx.serve.service.query.TcUserAuthorSubscribeQuery;

import java.util.List;

public interface TcUserAuthorSubscribeService {

    boolean insert(TcUserAuthorSubscribe tcUserAuthorSubscribe);

    void deleteByUserId(String userId);

    void deleteByUserIdAndAuthorId(String userId, String authorId);

    TcUserAuthorSubscribe getByUserIdAndAuthorId(String userId, String authorId);

    List<TcUserAuthorSubscribe> listAllByCondition(TcUserAuthorSubscribeQuery query);

    List<TcUserAuthorSubscribe> listByCondition(TcUserAuthorSubscribeQuery query);

    Integer countByCondition(TcUserAuthorSubscribeQuery query);

}
