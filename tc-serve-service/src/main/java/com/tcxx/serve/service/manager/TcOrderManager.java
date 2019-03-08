package com.tcxx.serve.service.manager;

import com.tcxx.serve.service.entity.TcOrder;
import com.tcxx.serve.service.query.TcOrderQuery;

import java.util.List;

public interface TcOrderManager {

    List<TcOrder> listByCondition(TcOrderQuery query);

    Integer countByCondition(TcOrderQuery query);

}
