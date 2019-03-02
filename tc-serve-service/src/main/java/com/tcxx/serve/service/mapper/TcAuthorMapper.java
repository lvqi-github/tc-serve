package com.tcxx.serve.service.mapper;

import com.tcxx.serve.service.entity.TcAuthor;
import com.tcxx.serve.service.query.TcAuthorQuery;

import java.util.List;

public interface TcAuthorMapper {

    int insert(TcAuthor tcAuthor);

    int update(TcAuthor tcAuthor);

    TcAuthor getByAuthorId(TcAuthorQuery query);

    List<TcAuthor> listAll();

    List<TcAuthor> listAllPage(TcAuthorQuery query);

    Integer countAll();
}
