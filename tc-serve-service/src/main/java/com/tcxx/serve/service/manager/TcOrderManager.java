package com.tcxx.serve.service.manager;

import com.tcxx.serve.service.entity.TcOrder;
import com.tcxx.serve.service.query.TcOrderQuery;

import java.util.Date;
import java.util.List;

public interface TcOrderManager {

    Long insert(TcOrder tcOrder);

    boolean updateOrderPayFinished(Long orderNo, String thirdPartyPayWaterNo, Date payFinishTime);

    boolean updateOrderPayFail(Long orderNo);

    boolean updateOrderPayClosed(Long orderNo);

    boolean updateOrderPayCancel(Long orderNo);

    TcOrder getByOrderNo(Long orderNo);

    List<TcOrder> listNotPayOrder(Integer pageSize);

    List<TcOrder> listByCondition(TcOrderQuery query);

    Integer countByCondition(TcOrderQuery query);

}
