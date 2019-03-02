package com.tcxx.serve.service.manager;

import com.tcxx.serve.service.entity.TcAuthor;

import java.util.List;

public interface TcAuthorManager {

    boolean insert(TcAuthor tcAuthor);

    boolean update(TcAuthor tcAuthor);

    TcAuthor getByAuthorId(String authorId);

    List<TcAuthor> listAll();

    List<TcAuthor> listAllPage(Integer page, Integer pageSize);

    Integer countAll();
}
