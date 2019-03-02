package com.tcxx.serve.web.domain.admin.resp;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ArticleListResp {

    /** 文章id **/
    private String articleId;

    /** 文章标题 **/
    private String articleTitle;

    /** 文章描述 **/
    private String articleDesc;

    /** 文章类型：1-足球 2-篮球 3-足篮混合 **/
    private Integer articleType;

    /** 文章类型 **/
    private String articleTypeName;

    /** 作者名称 **/
    private String authorName;

    /** 收费类型：1-收费 2-公开 **/
    private Integer chargeType;

    /** 收费类型 **/
    private String chargeTypeName;

    /** 价格 **/
    private BigDecimal price;

    /** 发布状态：1-已发布 2-未发布 **/
    private Integer releaseStatus;

    /** 发布状态 **/
    private String releaseStatusName;

    /** 文章状态：1-未开始 2-进行中 3-已结束 **/
    private Integer articleStatus;

    /** 文章状态 **/
    private String articleStatusName;

    /** 开始时间 **/
    private Date startTime;

    /** 结束时间 **/
    private Date endTime;

    /** 修改时间 **/
    private Date modified;

    /** 创建时间 **/
    private Date created;

}
