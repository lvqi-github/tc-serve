package com.tcxx.serve.service.query;

import lombok.Data;

@Data
public class TcUserAuthorSubscribeQuery extends Pagination {

    private String userId;

    private String authorId;

}
