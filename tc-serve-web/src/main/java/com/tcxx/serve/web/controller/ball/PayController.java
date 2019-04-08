package com.tcxx.serve.web.controller.ball;

import com.alibaba.fastjson.JSON;
import com.tcxx.serve.core.annotation.WeChatLoginTokenValidation;
import com.tcxx.serve.core.annotation.WeChatLoginUser;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.core.utils.IpUtil;
import com.tcxx.serve.service.TcArticleService;
import com.tcxx.serve.service.TcMemberRechargePackageService;
import com.tcxx.serve.service.TcOrderService;
import com.tcxx.serve.service.TcUserService;
import com.tcxx.serve.service.entity.TcArticle;
import com.tcxx.serve.service.entity.TcMemberRechargePackage;
import com.tcxx.serve.service.entity.TcOrder;
import com.tcxx.serve.service.entity.TcUser;
import com.tcxx.serve.service.enumtype.OrderPayChannelTypeEnum;
import com.tcxx.serve.service.enumtype.OrderTypeEnum;
import com.tcxx.serve.web.domain.ball.WeChatUser;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.enumtype.WeChatPayTradeTypeEnum;
import com.tcxx.serve.wechat.model.pay.UnifiedOrder;
import com.tcxx.serve.wechat.model.pay.UnifiedOrderResult;
import com.tcxx.serve.wechat.model.pay.WCPay;
import com.tcxx.serve.wechat.util.WeChatPaySignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/ball/pay")
public class PayController {

    @Autowired
    private TcUserService tcUserService;

    @Autowired
    private TcArticleService tcArticleService;

    @Autowired
    private TcMemberRechargePackageService tcMemberRechargePackageService;

    @Autowired
    private WeChatClient weChatClient;

    @Autowired
    private TcOrderService tcOrderService;

    @WeChatLoginTokenValidation
    @RequestMapping(value = "/articlePurchasePay", method = RequestMethod.POST)
    public Result<WCPay> articlePurchasePay(@WeChatLoginUser WeChatUser weChatUser, String articleId) {
        if (Objects.isNull(weChatUser)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "用户信息不能为空");
        }
        if (StringUtils.isBlank(articleId)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "articleId不能为空");
        }
        //获取用户信息
        TcUser tcUser = tcUserService.getByUserId(weChatUser.getUserId());
        if (Objects.isNull(tcUser)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "用户无效");
        }
        //获取文章信息
        TcArticle tcArticle = tcArticleService.getByArticleId(articleId);
        if (Objects.isNull(tcArticle)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "articleId无效");
        }

        // 创建系统内部订单
        Long orderNo = tcOrderService.insert(buildArticlePurchaseOrder(tcUser.getId(), tcArticle));

        // 请求微信统一下单
        UnifiedOrderResult unifiedOrderResult = weChatClient.pay().payUnifiedOrder(buildUnifiedOrder("推球汇-文章购买",
                String.valueOf(orderNo),
                tcArticle.getPrice().multiply(BigDecimal.valueOf(100)).intValue(),
                tcUser.getOpenId()));
        // 下单失败
        if (!unifiedOrderResult.isSuccess()){
            log.error("PayController#articlePurchasePay unifiedOrderResult fail, " +
                    "returnCode={}, returnMsg={}, resultCode={}, errCode={}, errCodeDes={}",
                    unifiedOrderResult.getReturnCode(), unifiedOrderResult.getReturnMsg(),
                    unifiedOrderResult.getResultCode(), unifiedOrderResult.getErrCode(), unifiedOrderResult.getErrCodeDes());
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR3002, "下单");
        }

        // 构建微信支付对象
        WCPay wcPay = buildWCPay(unifiedOrderResult.getPrepayId());

        Result<WCPay> result = ResultBuild.wrapSuccess();
        result.setValue(wcPay);
        return result;
    }

    @WeChatLoginTokenValidation
    @RequestMapping(value = "/memberRechargePay", method = RequestMethod.POST)
    public Result<WCPay> memberRechargePay(@WeChatLoginUser WeChatUser weChatUser, String rechargePackageId) {
        if (Objects.isNull(weChatUser)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "用户信息不能为空");
        }
        if (StringUtils.isBlank(rechargePackageId)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "rechargePackageId不能为空");
        }
        //获取用户信息
        TcUser tcUser = tcUserService.getByUserId(weChatUser.getUserId());
        if (Objects.isNull(tcUser)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "用户无效");
        }
        //获取充值套餐信息
        TcMemberRechargePackage rechargePackage = tcMemberRechargePackageService.getByPackageId(rechargePackageId);
        if (Objects.isNull(rechargePackage)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "rechargePackageId无效");
        }

        // 创建系统内部订单
        Long orderNo = tcOrderService.insert(buildMemberRechargeOrder(tcUser.getId(), rechargePackage));

        // 请求微信统一下单
        UnifiedOrderResult unifiedOrderResult = weChatClient.pay().payUnifiedOrder(buildUnifiedOrder("推球汇-会员充值",
                String.valueOf(orderNo),
                rechargePackage.getPrice().multiply(BigDecimal.valueOf(100)).intValue(),
                tcUser.getOpenId()));
        // 下单失败
        if (!unifiedOrderResult.isSuccess()){
            log.error("PayController#memberRechargePay unifiedOrderResult fail, " +
                            "returnCode={}, returnMsg={}, resultCode={}, errCode={}, errCodeDes={}",
                    unifiedOrderResult.getReturnCode(), unifiedOrderResult.getReturnMsg(),
                    unifiedOrderResult.getResultCode(), unifiedOrderResult.getErrCode(), unifiedOrderResult.getErrCodeDes());
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR3002, "下单");
        }

        // 构建微信支付对象
        WCPay wcPay = buildWCPay(unifiedOrderResult.getPrepayId());

        Result<WCPay> result = ResultBuild.wrapSuccess();
        result.setValue(wcPay);
        return result;
    }

    private TcOrder buildArticlePurchaseOrder(String userId, TcArticle tcArticle) {
        TcOrder order = new TcOrder();
        order.setOrderAmount(tcArticle.getPrice());
        order.setOrderType(OrderTypeEnum.ARTICLE_PURCHASE.getType());
        order.setUserId(userId);
        order.setPayChannelType(OrderPayChannelTypeEnum.WECHAT.getType());

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("articleId", tcArticle.getArticleId());

        order.setBusinessData(JSON.toJSONString(dataMap));
        return order;
    }

    private TcOrder buildMemberRechargeOrder(String userId, TcMemberRechargePackage rechargePackage) {
        TcOrder order = new TcOrder();
        order.setOrderAmount(rechargePackage.getPrice());
        order.setOrderType(OrderTypeEnum.MEMBER_RECHARGE.getType());
        order.setUserId(userId);
        order.setPayChannelType(OrderPayChannelTypeEnum.WECHAT.getType());

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("rechargePackageId", rechargePackage.getPackageId());

        order.setBusinessData(JSON.toJSONString(dataMap));
        return order;
    }

    private UnifiedOrder buildUnifiedOrder(String body, String outTradeNo, Integer totalFee, String openId) {
        UnifiedOrder unifiedOrder = new UnifiedOrder();
        unifiedOrder.setAppId(weChatClient.getWeChatConfiguration().getAppId());
        unifiedOrder.setMchId(weChatClient.getWeChatPayConfiguration().getMerchantId());
        unifiedOrder.setNonceStr(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15));
        unifiedOrder.setBody(body);
        unifiedOrder.setOutTradeNo(outTradeNo);
        unifiedOrder.setTotalFee(totalFee);
        unifiedOrder.setSpbillCreateIp(IpUtil.INTERNET_IP);
        unifiedOrder.setNotifyUrl(weChatClient.getWeChatPayConfiguration().getNotifyUrl());
        unifiedOrder.setTradeType(WeChatPayTradeTypeEnum.JSAPI.getType());
        unifiedOrder.setOpenId(openId);
        unifiedOrder.setSign(WeChatPaySignUtil.buildSign(unifiedOrder.toMap(), weChatClient.getWeChatPayConfiguration().getApiSecretKey(), true));
        return unifiedOrder;
    }

    private WCPay buildWCPay(String prepayId) {
        WCPay wcPay = new WCPay();
        wcPay.setAppId(weChatClient.getWeChatConfiguration().getAppId());
        wcPay.setTimeStamp(String.valueOf(System.currentTimeMillis() / 1000));
        wcPay.setNonceStr(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15));
        wcPay.setPackages(String.format("prepay_id=%s", prepayId));
        wcPay.setSignType("MD5");
        wcPay.setPaySign(WeChatPaySignUtil.buildSign(wcPay.toMap(), weChatClient.getWeChatPayConfiguration().getApiSecretKey(), true));
        return wcPay;
    }

}
