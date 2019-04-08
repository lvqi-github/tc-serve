package com.tcxx.serve.web.controller.admin;

import com.tcxx.serve.core.annotation.AdminLoginTokenValidation;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.TcOrderService;
import com.tcxx.serve.service.entity.TcOrder;
import com.tcxx.serve.service.enumtype.OrderPayChannelTypeEnum;
import com.tcxx.serve.service.enumtype.OrderPayStatusEnum;
import com.tcxx.serve.service.enumtype.OrderTypeEnum;
import com.tcxx.serve.service.query.TcOrderQuery;
import com.tcxx.serve.web.domain.Page;
import com.tcxx.serve.web.domain.admin.query.OrderListReqQuery;
import com.tcxx.serve.web.domain.admin.resp.OrderListResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/admin/order")
public class OrderController {

    @Autowired
    private TcOrderService tcOrderService;

    @AdminLoginTokenValidation
    @RequestMapping("/getOrderTypeList")
    public Result<Map<String, Object>> getOrderTypeList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (OrderTypeEnum orderTypeEnum : OrderTypeEnum.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("label", orderTypeEnum.getName());
            map.put("value", orderTypeEnum.getType());
            list.add(map);
        }
        Result<Map<String, Object>> result = ResultBuild.wrapSuccess();
        result.setValues(list);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping("/getOrderPayStatusList")
    public Result<Map<String, Object>> getOrderPayStatusList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (OrderPayStatusEnum orderPayStatusEnum : OrderPayStatusEnum.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("label", orderPayStatusEnum.getName());
            map.put("value", orderPayStatusEnum.getStatus());
            list.add(map);
        }
        Result<Map<String, Object>> result = ResultBuild.wrapSuccess();
        result.setValues(list);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping("/getOrderList")
    public Result<OrderListResp> getOrderList(OrderListReqQuery reqQuery, Page page) {
        if (Objects.isNull(page)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page信息不能为空");
        }
        if (Objects.isNull(page.getPage()) || page.getPage() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page不能为空且大于0");
        }
        if (Objects.isNull(page.getPageSize()) || page.getPageSize() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "pageSize不能为空且大于0");
        }

        TcOrderQuery query = buildGetOrderListQuery(reqQuery, page);

        Integer count = tcOrderService.countByCondition(query);
        List<TcOrder> orderList = tcOrderService.listByCondition(query);

        List<OrderListResp> orderListResps = new ArrayList<>();
        orderList.stream().forEach(bean -> {
            OrderListResp resp = new OrderListResp();
            resp.setOrderNo(bean.getOrderNo());
            resp.setOrderAmount(bean.getOrderAmount());
            resp.setOrderType(bean.getOrderType());
            resp.setOrderTypeName(OrderTypeEnum.getByType(bean.getOrderType()));
            resp.setOrderPayStatus(bean.getOrderPayStatus());
            resp.setOrderPayStatusName(OrderPayStatusEnum.getByStatus(bean.getOrderPayStatus()));
            resp.setUserId(bean.getUserId());
            resp.setPayChannelType(bean.getPayChannelType());
            resp.setPayChannelTypeName(OrderPayChannelTypeEnum.getByType(bean.getPayChannelType()));
            resp.setPayFinishTime(bean.getPayFinishTime());
            resp.setThirdPartyPayWaterNo(bean.getThirdPartyPayWaterNo());
            resp.setBusinessData(bean.getBusinessData());
            resp.setModified(bean.getModified());
            resp.setCreated(bean.getCreated());
            orderListResps.add(resp);
        });

        Result<OrderListResp> result = ResultBuild.wrapSuccess();
        result.setTotalElements(count);
        result.setValues(orderListResps);
        return result;
    }

    private TcOrderQuery buildGetOrderListQuery(OrderListReqQuery reqQuery, Page page) {
        TcOrderQuery query = new TcOrderQuery();
        query.setOrderNo(reqQuery.getOrderNo());
        query.setUserId(reqQuery.getUserId());
        query.setOrderType(reqQuery.getOrderType());
        query.setOrderPayStatus(reqQuery.getOrderPayStatus());
        query.setPagingPageCurrent(page.getPage());
        query.setPagingPageSize(page.getPageSize());
        return query;
    }

}
