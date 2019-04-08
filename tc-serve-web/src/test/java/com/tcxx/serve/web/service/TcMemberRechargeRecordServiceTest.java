package com.tcxx.serve.web.service;

import com.tcxx.serve.service.TcMemberRechargeRecordService;
import com.tcxx.serve.service.TcOrderService;
import com.tcxx.serve.service.entity.TcOrder;
import com.tcxx.serve.web.TcServeWebApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class TcMemberRechargeRecordServiceTest extends TcServeWebApplicationTests {

    @Autowired
    private TcOrderService tcOrderService;

    @Autowired
    private TcMemberRechargeRecordService tcMemberRechargeRecordService;

    @Test
    public void testHandlerMemberRecharge(){
        TcOrder tcOrder = tcOrderService.getByOrderNo(123232322L);
        tcMemberRechargeRecordService.handlerMemberRecharge(tcOrder, "wx123423412dsddf", new Date());
    }
}
