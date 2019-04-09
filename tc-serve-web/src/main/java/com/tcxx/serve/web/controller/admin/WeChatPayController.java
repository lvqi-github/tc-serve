package com.tcxx.serve.web.controller.admin;

import com.tcxx.serve.core.annotation.PassRequestSignValidation;
import com.tcxx.serve.core.annotation.PassTokenValidation;
import com.tcxx.serve.core.redis.RedisKeyPrefix;
import com.tcxx.serve.core.utils.LockUtil;
import com.tcxx.serve.core.utils.XStreamUtil;
import com.tcxx.serve.service.TcArticlePurchaseRecordService;
import com.tcxx.serve.service.TcMemberRechargeRecordService;
import com.tcxx.serve.service.TcOrderService;
import com.tcxx.serve.service.entity.TcOrder;
import com.tcxx.serve.service.enumtype.OrderPayStatusEnum;
import com.tcxx.serve.service.enumtype.OrderTypeEnum;
import com.tcxx.serve.uid.utils.DateUtils;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.model.pay.PayNotifyResult;
import com.tcxx.serve.wechat.util.WeChatPaySignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admin/weChatPay")
public class WeChatPayController {

    @Autowired
    private WeChatClient weChatClient;

    @Autowired
    private TcOrderService tcOrderService;

    @Autowired
    private TcMemberRechargeRecordService tcMemberRechargeRecordService;

    @Autowired
    private TcArticlePurchaseRecordService tcArticlePurchaseRecordService;

    @Autowired
    private LockUtil lockUtil;

    @PassRequestSignValidation
    @PassTokenValidation
    @RequestMapping(value = "/notifyPayResult", method = RequestMethod.POST)
    public String notifyPayResult(@RequestBody String requestBody) {
        // 解析请求
        PayNotifyResult payNotifyResult = XStreamUtil.getInstance().xmlToBean(requestBody, PayNotifyResult.class);
        // 校验消息是否合法
        String sign = WeChatPaySignUtil.buildSign(payNotifyResult.toMap(), weChatClient.getWeChatPayConfiguration().getApiSecretKey(), true);
        if (!sign.equals(payNotifyResult.getSign())){
            // 非法请求不处理
            log.error("WeChatPayController#notifyPayResult 签名验证失败, sign={}, requestBody={}", sign, requestBody);
            return buildFailResult();
        }
        // 校验订单信息
        TcOrder tcOrder = tcOrderService.getByOrderNo(Long.valueOf(payNotifyResult.getOutTradeNo()));
        if (Objects.isNull(tcOrder)){
            // 非法请求不处理
            log.error("WeChatPayController#notifyPayResult 订单无效, PayNotifyResult={}", payNotifyResult);
            return buildFailResult();
        }
        // 校验金额
        if(!payNotifyResult.getTotalFee().equals(tcOrder.getOrderAmount().multiply(BigDecimal.valueOf(100)).intValue())){
            // 非法请求不处理
            log.error("WeChatPayController#notifyPayResult 订单金额校验失败, PayNotifyResult={}", payNotifyResult);
            return buildFailResult();
        }
        // 校验订单状态
        if (!tcOrder.getOrderPayStatus().equals(OrderPayStatusEnum.DID_NOT_PAY.getStatus())){
            //已经处理过 返回成功
            return buildSuccessResult();
        }
        // 获取请求锁
        if (!lockUtil.lock(RedisKeyPrefix.getTcAdminRedisKey(RedisKeyPrefix.TC_ADMIN_WECHAT_PAY_NOTIFY, String.valueOf(tcOrder.getOrderNo())),
                UUID.randomUUID().toString().replaceAll("-", ""), 10)){ // 10秒的锁
            //没拿到锁 直接返回
            return buildFailResult();
        }
        // 支付完成时间
        Date payFinishedTime = DateUtils.parseDate(payNotifyResult.getTimeEnd(), "yyyyMMddHHmmss");
        // 处理业务逻辑
        if (!payNotifyResult.isSuccess()){// 支付失败-此处不做处理 等定时任务扫描 统一处理
            log.error("WeChatPayController#notifyPayResult returnCode={}, returnMsg={}, resultCode={}, errCode={}, errCodeDes={}",
                    payNotifyResult.getReturnCode(), payNotifyResult.getReturnMsg(),
                    payNotifyResult.getResultCode(), payNotifyResult.getErrCode(), payNotifyResult.getErrCodeDes());
        }else {
            if (tcOrder.getOrderType().equals(OrderTypeEnum.MEMBER_RECHARGE.getType())){// 会员充值订单
                tcMemberRechargeRecordService.handlerMemberRecharge(tcOrder, payNotifyResult.getTransactionId(), payFinishedTime);
            }else if(tcOrder.getOrderType().equals(OrderTypeEnum.ARTICLE_PURCHASE.getType())) {// 文章购买订单
                tcArticlePurchaseRecordService.handlerArticlePurchase(tcOrder, payNotifyResult.getTransactionId(), payFinishedTime);
            }
        }

        // 构建返回结果
        return buildSuccessResult();
    }

    private String buildSuccessResult() {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        sb.append("<return_code><![CDATA[").append("SUCCESS").append("]]></return_code>");
        sb.append("<return_msg><![CDATA[").append("OK").append("]]></return_msg>");
        sb.append("</xml>");
        return sb.toString();
    }

    private String buildFailResult() {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        sb.append("<return_code><![CDATA[").append("FAIL").append("]]></return_code>");
        sb.append("<return_msg><![CDATA[").append("FAIL").append("]]></return_msg>");
        sb.append("</xml>");
        return sb.toString();
    }

}
