package com.tcxx.serve.service.manager.impl;

import com.tcxx.serve.service.entity.TcArticle;
import com.tcxx.serve.service.enumtype.ArticlePushJobStatusEnum;
import com.tcxx.serve.service.manager.TcArticleManager;
import com.tcxx.serve.service.mapper.TcArticleMapper;
import com.tcxx.serve.service.query.TcArticleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TcArticleManagerImpl implements TcArticleManager {

    @Autowired
    private TcArticleMapper tcArticleMapper;

    @Override
    public boolean insert(TcArticle tcArticle) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        tcArticle.setArticleId(uuid);
        tcArticle.setPushJobStatus(ArticlePushJobStatusEnum.PUSH_JOB_NOT_GENERATED.getStatus());
        int i = tcArticleMapper.insert(tcArticle);
        return 1 == i;
    }

    @Override
    public boolean update(TcArticle tcArticle) {
        return 1 == tcArticleMapper.update(tcArticle);
    }

    @Override
    public boolean updatePushJobStatusGenerated(String articleId) {
        TcArticle tcArticle = new TcArticle();
        tcArticle.setArticleId(articleId);
        return 1 == tcArticleMapper.updatePushJobStatusGenerated(tcArticle);
    }

    @Override
    public TcArticle getByArticleId(String articleId) {
        TcArticleQuery query = new TcArticleQuery();
        query.setArticleId(articleId);
        return tcArticleMapper.getByArticleId(query);
    }

    @Override
    public List<TcArticle> listByCondition(TcArticleQuery query) {
        return tcArticleMapper.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcArticleQuery query) {
        return tcArticleMapper.countByCondition(query);
    }
}
