package com.tcxx.serve.service.query;

import lombok.Data;

@Data
public class TcPublicAccountFocusQuery extends Pagination {

    /** 防重uuid（MD5（公众号微信id_openId）） **/
    private String uuid;

}
