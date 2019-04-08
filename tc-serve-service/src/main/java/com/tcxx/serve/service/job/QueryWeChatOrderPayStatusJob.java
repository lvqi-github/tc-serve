package com.tcxx.serve.service.job;

import com.tcxx.serve.service.TcArticlePurchaseRecordService;
import com.tcxx.serve.service.TcMemberRechargeRecordService;
import com.tcxx.serve.service.TcOrderService;
import com.tcxx.serve.service.entity.TcOrder;
import com.tcxx.serve.service.entity.TcTemplateMessagePush;
import com.tcxx.serve.service.enumtype.OrderTypeEnum;
import com.tcxx.serve.uid.utils.DateUtils;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.enumtype.WeChatPayTradeStateEnum;
import com.tcxx.serve.wechat.model.pay.CloseOrder;
import com.tcxx.serve.wechat.model.pay.CloseOrderResult;
import com.tcxx.serve.wechat.model.pay.OrderQuery;
import com.tcxx.serve.wechat.model.pay.OrderQueryResult;
import com.tcxx.serve.wechat.util.WeChatPaySignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component("queryWeChatOrderPayStatusJob")
public class QueryWeChatOrderPayStatusJob {

    @Autowired
    private TcOrderService tcOrderService;

    @Autowired
    private TcMemberRechargeRecordService tcMemberRechargeRecordService;

    @Autowired
    private TcArticlePurchaseRecordService tcArticlePurchaseRecordService;

    @Autowired
    private WeChatClient weChatClient;

    public void execute() {
        try {
            // 获取未支付状态的订单 每次job执行获取100条
            int pageSize = 100;
            List<TcOrder> tcOrderList = tcOrderService.listNotPayOrder(pageSize);
            for (TcOrder tcOrder : tcOrderList) {
                try {
                    OrderQueryResult orderQueryResult = weChatClient.pay().payOrderQuery(buildOrderQuery(String.valueOf(tcOrder.getOrderNo())));
                    if (orderQueryResult.getReturnCode().equals("SUCCESS")){ // 只处理通信成功的
                        if (orderQueryResult.getResultCode().equals("SUCCESS")){
                            if (orderQueryResult.getTradeState().equals(WeChatPayTradeStateEnum.SUCCESS.getState())){
                                // 支付成功
                                Date payFinishedTime = DateUtils.parseDate(orderQueryResult.getTimeEnd(), "yyyyMMddHHmmss"); // 支付完成时间
                                if (tcOrder.getOrderType().equals(OrderTypeEnum.MEMBER_RECHARGE.getType())){// 会员充值订单
                                    tcMemberRechargeRecordService.handlerMemberRecharge(tcOrder, orderQueryResult.getTransactionId(), payFinishedTime);
                                }else if(tcOrder.getOrderType().equals(OrderTypeEnum.ARTICLE_PURCHASE.getType())) {// 文章购买订单
                                    tcArticlePurchaseRecordService.handlerArticlePurchase(tcOrder, orderQueryResult.getTransactionId(), payFinishedTime);
                                }
                            }else if(orderQueryResult.getTradeState().equals(WeChatPayTradeStateEnum.NOTPAY.getState())) {
                                // 未支付 关闭订单
                                CloseOrderResult closeOrderResult = weChatClient.pay().payCloseOrder(buildCloseOrder(String.valueOf(tcOrder.getOrderNo())));
                                if (closeOrderResult.isSuccess()){
                                    tcOrderService.updateOrderPayClosed(tcOrder.getOrderNo());
                                }else {
                                    log.error("QueryWeChatOrderPayStatusJob#execute closeOrderResult fail, " +
                                                    "returnCode={}, returnMsg={}, resultCode={}, errCode={}, errCodeDes={}",
                                            closeOrderResult.getReturnCode(), closeOrderResult.getReturnMsg(),
                                            closeOrderResult.getResultCode(), closeOrderResult.getErrCode(), closeOrderResult.getErrCodeDes());
                                }
                            }else if(orderQueryResult.getTradeState().equals(WeChatPayTradeStateEnum.CLOSED.getState())) {
                                // 订单已关闭
                                tcOrderService.updateOrderPayClosed(tcOrder.getOrderNo());
                            }else if(orderQueryResult.getTradeState().equals(WeChatPayTradeStateEnum.PAYERROR.getState())) {
                                // 支付失败
                                tcOrderService.updateOrderPayFail(tcOrder.getOrderNo());
                            }
                        }else {
                            if (orderQueryResult.getErrCode().equals("ORDERNOTEXIST")){
                                // 微信支付系统中无此订单号 修改订单状态为 已作废
                                tcOrderService.updateOrderPayCancel(tcOrder.getOrderNo());
                            }
                        }
                    }else {
                        log.error("QueryWeChatOrderPayStatusJob#execute payOrderQuery 通信异常, returnMsg={}", orderQueryResult.getReturnMsg());
                    }
                }catch (Exception e) {
                    log.error("QueryWeChatOrderPayStatusJob#execute handler error, orderNo={}, e={}", tcOrder.getOrderNo(), e);
                }
                Thread.sleep(500); //休眠500毫秒
            }
        }catch (Exception e){
            log.error("QueryWeChatOrderPayStatusJob#execute error", e);
        }
    }

    private OrderQuery buildOrderQuery(String orderNo) {
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.setAppId(weChatClient.getWeChatConfiguration().getAppId());
        orderQuery.setMchId(weChatClient.getWeChatPayConfiguration().getMerchantId());
        orderQuery.setOutTradeNo(orderNo);
        orderQuery.setNonceStr(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15));
        orderQuery.setSign(WeChatPaySignUtil.buildSign(orderQuery.toMap(), weChatClient.getWeChatPayConfiguration().getApiSecretKey(), true));
        return orderQuery;
    }

    private CloseOrder buildCloseOrder(String orderNo) {
        CloseOrder closeOrder = new CloseOrder();
        closeOrder.setAppId(weChatClient.getWeChatConfiguration().getAppId());
        closeOrder.setMchId(weChatClient.getWeChatPayConfiguration().getMerchantId());
        closeOrder.setOutTradeNo(orderNo);
        closeOrder.setNonceStr(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15));
        closeOrder.setSign(WeChatPaySignUtil.buildSign(closeOrder.toMap(), weChatClient.getWeChatPayConfiguration().getApiSecretKey(), true));
        return closeOrder;
    }
}
