package com.tcxx.serve.core.qiniu;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QiNiuUploadConfig {

    /**
     * 七牛云配置
     */
    @Autowired
    private QiNiuProperties qiNiuProperties;

    /**
     * 机房 默认华东
     */
    @Bean
    public com.qiniu.storage.Configuration qiNiuConfig() {
        Zone zone;
        switch (qiNiuProperties.getZone()){
            case "zone0":
                zone = Zone.zone0();
                break;
            case "zone1":
                zone = Zone.zone1();
                break;
            case "zone2":
                zone = Zone.zone2();
                break;
            case "zoneNa0":
                zone = Zone.zoneNa0();
                break;
            case "zoneAs0" :
                zone = Zone.zoneAs0();
                break;
            default:
                zone = Zone.zone0();
        }
        return new com.qiniu.storage.Configuration(zone);
    }

    /**
     * 构建一个七牛上传工具实例
     */
    @Bean
    public UploadManager uploadManager() {
        return new UploadManager(qiNiuConfig());
    }

    /**
     * 认证信息实例
     *
     * @return
     */
    @Bean
    public Auth auth() {
        return Auth.create(qiNiuProperties.getAccessKey(),
                qiNiuProperties.getSecretKey());
    }

    /**
     * 构建七牛空间管理实例
     */
    @Bean
    public BucketManager bucketManager() {
        return new BucketManager(auth(), qiNiuConfig());
    }

    /**
     * 配置gson为json解析工具
     *
     * @return
     */
    @Bean
    public Gson gson() {
        return new Gson();
    }

}
