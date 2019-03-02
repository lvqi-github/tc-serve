package com.tcxx.serve.service.mapper;

import com.tcxx.serve.service.entity.TcArticle;
import com.tcxx.serve.service.query.TcArticleQuery;

import java.util.List;

public interface TcArticleMapper {

    int insert(TcArticle tcArticle);

    int update(TcArticle tcArticle);

    TcArticle getByArticleId(TcArticleQuery query);

    List<TcArticle> listByCondition(TcArticleQuery query);

    Integer countByCondition(TcArticleQuery query);

}
