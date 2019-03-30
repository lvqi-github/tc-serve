package com.tcxx.serve.service.mapper;

import com.tcxx.serve.service.entity.TcMemberRechargePackage;
import com.tcxx.serve.service.query.TcMemberRechargePackageQuery;

import java.util.List;

public interface TcMemberRechargePackageMapper {

    int insert(TcMemberRechargePackage tcMemberRechargePackage);

    int update(TcMemberRechargePackage tcMemberRechargePackage);

    TcMemberRechargePackage getByPackageId(TcMemberRechargePackageQuery query);

    List<TcMemberRechargePackage> listEnableAll();

    List<TcMemberRechargePackage> listAllPage(TcMemberRechargePackageQuery query);

    Integer countAll();

}
