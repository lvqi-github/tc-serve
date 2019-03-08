package com.tcxx.serve.service.mapper;

import com.tcxx.serve.service.entity.TcOrder;
import com.tcxx.serve.service.query.TcOrderQuery;

import java.util.List;

public interface TcOrderMapper {

    int insert(TcOrder tcOrder);

    int updateOrderPayFinished(TcOrder tcOrder);

    TcOrder getByOrderNo(TcOrderQuery query);

    List<TcOrder> listByCondition(TcOrderQuery query);

    Integer countByCondition(TcOrderQuery query);

}
