package com.tcxx.serve.service.query;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TcArticleQuery extends Pagination {

    /** 文章id **/
    private String articleId;

    /** 发布状态：1-已发布 2-未发布 **/
    private Integer releaseStatus;

    /** 文章状态集合：1-未开始 2-进行中 3-已结束 **/
    private List<Integer> articleStatusList;

    /** 作者id **/
    private String authorId;

    /** 创建时间开始 **/
    private Date createdStart;

    /** 创建时间结束 **/
    private Date createdEnd;
}
