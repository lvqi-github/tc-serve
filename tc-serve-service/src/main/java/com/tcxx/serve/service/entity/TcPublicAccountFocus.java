package com.tcxx.serve.service.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TcPublicAccountFocus {

    /** 主键 **/
    private String id;

    /** 公众号微信id **/
    private String publicAccountWechatId;

    private String openId;

    /** 防重uuid（MD5（公众号微信id_openId）） **/
    private String uuid;

    private Date created;
}
