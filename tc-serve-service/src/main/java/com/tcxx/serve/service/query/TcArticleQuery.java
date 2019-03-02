package com.tcxx.serve.service.query;

import lombok.Data;

import java.util.Date;

@Data
public class TcArticleQuery extends Pagination {

    /** 文章id **/
    private String articleId;

    /** 创建时间开始 **/
    private Date createdStart;

    /** 创建时间结束 **/
    private Date createdEnd;
}
