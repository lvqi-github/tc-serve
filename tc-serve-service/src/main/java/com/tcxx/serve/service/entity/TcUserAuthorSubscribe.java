package com.tcxx.serve.service.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TcUserAuthorSubscribe {

    private String userId;

    private String authorId;

    private Date created;
}
