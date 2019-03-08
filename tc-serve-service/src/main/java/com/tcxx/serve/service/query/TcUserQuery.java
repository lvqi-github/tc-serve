package com.tcxx.serve.service.query;

import lombok.Data;

@Data
public class TcUserQuery extends Pagination {

    /** 用户id **/
    private String id;

    private String openId;

    /** 微信用户昵称 **/
    private String nickName;

}
