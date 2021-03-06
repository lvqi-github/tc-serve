package com.tcxx.serve.web.service;

import com.tcxx.serve.service.TcUserService;
import com.tcxx.serve.service.entity.TcUser;
import com.tcxx.serve.web.TcServeWebApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class TcUserServiceTest extends TcServeWebApplicationTests {

    @Autowired
    private TcUserService tcUserService;

    @Test
    public void testInsert(){
        TcUser tcUser = new TcUser();
        tcUser.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        tcUser.setNickName("asdfsdf");
        tcUserService.insert(tcUser);
    }

    @Test
    public void testGetByUserId() {
        TcUser user = tcUserService.getByUserId("cb31c4c127bb457dbf099bc3f7bec1fb");
        System.out.println(user);
    }
}
