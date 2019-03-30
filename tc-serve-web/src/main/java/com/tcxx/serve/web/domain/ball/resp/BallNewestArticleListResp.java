package com.tcxx.serve.web.domain.ball.resp;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BallNewestArticleListResp {

    /** 文章id **/
    private String articleId;

    /** 文章标题 **/
    private String articleTitle;

    /** 文章描述 **/
    private String articleDesc;

    /** 文章类型：1-足球 2-篮球 3-足篮混合 **/
    private Integer articleType;

    /** 收费类型：1-收费 2-公开 **/
    private Integer chargeType;

    /** 价格 **/
    private BigDecimal price;

    /** 文章状态：1-未开始 2-进行中 3-已结束 **/
    private Integer articleStatus;

    /** 创建时间 **/
    private Date created;

}
