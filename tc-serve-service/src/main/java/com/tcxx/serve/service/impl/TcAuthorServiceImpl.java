package com.tcxx.serve.service.impl;

import com.tcxx.serve.service.TcAuthorService;
import com.tcxx.serve.service.entity.TcAuthor;
import com.tcxx.serve.service.manager.TcAuthorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcAuthorServiceImpl implements TcAuthorService {

    @Autowired
    private TcAuthorManager tcAuthorManager;

    @Override
    public boolean insert(TcAuthor tcAuthor) {
        return tcAuthorManager.insert(tcAuthor);
    }

    @Override
    public boolean update(TcAuthor tcAuthor) {
        return tcAuthorManager.update(tcAuthor);
    }

    @Override
    public TcAuthor getByAuthorId(String authorId) {
        return tcAuthorManager.getByAuthorId(authorId);
    }

    @Override
    public List<TcAuthor> listAll() {
        return tcAuthorManager.listAll();
    }

    @Override
    public List<TcAuthor> listAllPage(Integer page, Integer pageSize) {
        return tcAuthorManager.listAllPage(page, pageSize);
    }

    @Override
    public Integer countAll() {
        return tcAuthorManager.countAll();
    }
}
