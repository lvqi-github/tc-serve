package com.tcxx.serve.service.mapper;

import com.tcxx.serve.service.entity.TcUserAuthorSubscribe;
import com.tcxx.serve.service.query.TcUserAuthorSubscribeQuery;

import java.util.List;

public interface TcUserAuthorSubscribeMapper {

    int insert(TcUserAuthorSubscribe tcUserAuthorSubscribe);

    int deleteByUserId(TcUserAuthorSubscribe tcUserAuthorSubscribe);

    int deleteByUserIdAndAuthorId(TcUserAuthorSubscribe tcUserAuthorSubscribe);

    TcUserAuthorSubscribe getByUserIdAndAuthorId(TcUserAuthorSubscribeQuery query);

    List<TcUserAuthorSubscribe> listAllByCondition(TcUserAuthorSubscribeQuery query);

    List<TcUserAuthorSubscribe> listByCondition(TcUserAuthorSubscribeQuery query);

    Integer countByCondition(TcUserAuthorSubscribeQuery query);

}
