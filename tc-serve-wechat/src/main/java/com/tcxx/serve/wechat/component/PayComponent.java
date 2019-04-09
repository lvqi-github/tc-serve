package com.tcxx.serve.wechat.component;

import com.tcxx.serve.core.http.HttpClientUtil;
import com.tcxx.serve.core.utils.XStreamUtil;
import com.tcxx.serve.wechat.WeChatClient;
import com.tcxx.serve.wechat.model.pay.*;

public class PayComponent extends AbstractComponent {

    public PayComponent(WeChatClient weChatClient) {
        super(weChatClient);
    }

    /**
     * 统一下单
     *
     * @param unifiedOrder 统一下单对象
     * @return 下单返回结果对象
     */
    public UnifiedOrderResult payUnifiedOrder(UnifiedOrder unifiedOrder) {
        //将统一下单对象转成XML
        String xmlPost = unifiedOrder.toXML();

        String xmlPostResult = HttpClientUtil.doPostXml("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlPost);
//        String xmlPostResult = HttpClientUtil.doPostXml("https://api.mch.weixin.qq.com/sandboxnew/pay/unifiedorder", xmlPost); //沙箱环境

        UnifiedOrderResult unifiedOrderResult = XStreamUtil.getInstance().xmlToBean(xmlPostResult, UnifiedOrderResult.class);
        return unifiedOrderResult;
    }

    /**
     * 查询订单
     *
     * @param orderQuery 订单查询对象
     * @return 订单查询结果
     */
    public OrderQueryResult payOrderQuery(OrderQuery orderQuery){
        //将统一下单对象转成XML
        String xmlPost = orderQuery.toXML();

        String xmlPostResult = HttpClientUtil.doPostXml("https://api.mch.weixin.qq.com/pay/orderquery", xmlPost);
//        String xmlPostResult = HttpClientUtil.doPostXml("https://api.mch.weixin.qq.com/sandboxnew/pay/orderquery", xmlPost); //沙箱环境

        OrderQueryResult orderQueryResult = XStreamUtil.getInstance().xmlToBean(xmlPostResult, OrderQueryResult.class);
        return orderQueryResult;
    }

    /**
     * 关闭订单
     *
     * @param closeOrder 关闭订单对象
     * @return 关闭订单结果
     */
    public CloseOrderResult payCloseOrder(CloseOrder closeOrder){
        //将统一下单对象转成XML
        String xmlPost = closeOrder.toXML();

        String xmlPostResult = HttpClientUtil.doPostXml("https://api.mch.weixin.qq.com/pay/closeorder", xmlPost);
//        String xmlPostResult = HttpClientUtil.doPostXml("https://api.mch.weixin.qq.com/sandboxnew/pay/closeorder", xmlPost); //沙箱环境

        CloseOrderResult closeOrderResult = XStreamUtil.getInstance().xmlToBean(xmlPostResult, CloseOrderResult.class);
        return closeOrderResult;
    }

    /**
     * 获取沙箱环境API秘钥
     * @param xml
     * @return
     */
    public String getSandboxSignKey(String xml){
        return HttpClientUtil.doPostXml("https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey", xml); //沙箱环境
    }

}
