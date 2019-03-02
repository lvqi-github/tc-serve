package com.tcxx.serve.service.manager.impl;

import com.tcxx.serve.service.entity.TcAuthor;
import com.tcxx.serve.service.manager.TcAuthorManager;
import com.tcxx.serve.service.mapper.TcAuthorMapper;
import com.tcxx.serve.service.query.TcAuthorQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TcAuthorManagerImpl implements TcAuthorManager {

    @Autowired
    private TcAuthorMapper tcAuthorMapper;

    @Override
    public boolean insert(TcAuthor tcAuthor) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        tcAuthor.setAuthorId(uuid);
        int i = tcAuthorMapper.insert(tcAuthor);
        return 1 == i;
    }

    @Override
    public boolean update(TcAuthor tcAuthor) {
        return 1 == tcAuthorMapper.update(tcAuthor);
    }

    @Override
    public TcAuthor getByAuthorId(String authorId) {
        TcAuthorQuery query = new TcAuthorQuery();
        query.setAuthorId(authorId);
        return tcAuthorMapper.getByAuthorId(query);
    }

    @Override
    public List<TcAuthor> listAll() {
        return tcAuthorMapper.listAll();
    }

    @Override
    public List<TcAuthor> listAllPage(Integer page, Integer pageSize) {
        TcAuthorQuery query = new TcAuthorQuery();
        query.setPagingPageCurrent(page);
        query.setPagingPageSize(pageSize);
        return tcAuthorMapper.listAllPage(query);
    }

    @Override
    public Integer countAll() {
        return tcAuthorMapper.countAll();
    }
}
