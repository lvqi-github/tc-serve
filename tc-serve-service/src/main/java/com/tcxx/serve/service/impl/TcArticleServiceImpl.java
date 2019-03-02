package com.tcxx.serve.service.impl;

import com.tcxx.serve.service.TcArticleService;
import com.tcxx.serve.service.entity.TcArticle;
import com.tcxx.serve.service.manager.TcArticleManager;
import com.tcxx.serve.service.query.TcArticleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcArticleServiceImpl implements TcArticleService {

    @Autowired
    private TcArticleManager tcArticleManager;

    @Override
    public boolean insert(TcArticle tcArticle) {
        return tcArticleManager.insert(tcArticle);
    }

    @Override
    public boolean update(TcArticle tcArticle) {
        return tcArticleManager.update(tcArticle);
    }

    @Override
    public TcArticle getByArticleId(String articleId) {
        return tcArticleManager.getByArticleId(articleId);
    }

    @Override
    public List<TcArticle> listByCondition(TcArticleQuery query) {
        return tcArticleManager.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcArticleQuery query) {
        return tcArticleManager.countByCondition(query);
    }
}
