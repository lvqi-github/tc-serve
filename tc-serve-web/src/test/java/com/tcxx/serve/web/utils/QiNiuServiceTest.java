package com.tcxx.serve.web.utils;

import com.tcxx.serve.core.qiniu.QiNiuService;
import com.tcxx.serve.web.TcServeWebApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.UUID;

public class QiNiuServiceTest extends TcServeWebApplicationTests {

    @Autowired
    private QiNiuService qiNiuService;

    @Test
    public void testUpload() {
        File file = new File("d:/football.jpg");
        String fileName = file.getName();
        String qiNiuFileName = UUID.randomUUID().toString().replaceAll("-", "") + fileName.substring(fileName.lastIndexOf("."));
        String path = qiNiuService.uploadFile(file, qiNiuFileName);
        System.out.println(path);
    }
}
