package com.tcxx.serve.web.uid;

import com.tcxx.serve.uid.UidGenerator;
import com.tcxx.serve.web.TcServeWebApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CachedUidGeneratorTest extends TcServeWebApplicationTests {

    @Autowired
    private UidGenerator cachedUidGenerator;

    @Test
    public void testUid() {
        System.out.println(cachedUidGenerator.getUID());
    }

}
