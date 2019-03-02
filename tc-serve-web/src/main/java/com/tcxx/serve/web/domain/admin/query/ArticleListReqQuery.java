package com.tcxx.serve.web.domain.admin.query;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ArticleListReqQuery {

    /** 发布时间开始 **/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date releaseTimeStart;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    /** 发布时间结束 **/
    private Date releaseTimeEnd;
}
