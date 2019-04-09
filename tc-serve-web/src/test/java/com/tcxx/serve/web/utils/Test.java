package com.tcxx.serve.web.utils;

import com.tcxx.serve.uid.utils.DateUtils;
import com.tcxx.serve.wechat.model.pay.PayNotifyResult;

public class Test {

    public static void main(String[] args) {
//        System.out.println(DateUtils.parseDate("20190407090844", "yyyyMMddHHmmss"));
        PayNotifyResult payNotifyResult = new PayNotifyResult();
        String aa = String.valueOf(payNotifyResult.getSettlementTotalFee());
        System.out.println(aa);
    }
}
