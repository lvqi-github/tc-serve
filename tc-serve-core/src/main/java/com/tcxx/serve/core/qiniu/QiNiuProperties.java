package com.tcxx.serve.core.qiniu;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "qiniu")
public class QiNiuProperties {

    /** 七牛accessKey **/
    private String accessKey;

    /** 七牛secretKey **/
    private String secretKey;

    /** 七牛存储空间名称 **/
    private String bucket;

    /** [{'zone0':'华东'}, {'zone1':'华北'},{'zone2':'华南'},{'zoneNa0':'北美'},{'zoneAs0':'东南亚'}] **/
    private String zone;

    /** 外链默认域名 **/
    private String cdnPrefixOfBucket;

}
