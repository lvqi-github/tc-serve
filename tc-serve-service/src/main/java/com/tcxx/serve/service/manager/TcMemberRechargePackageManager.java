package com.tcxx.serve.service.manager;

import com.tcxx.serve.service.entity.TcMemberRechargePackage;

import java.util.List;

public interface TcMemberRechargePackageManager {

    boolean insert(TcMemberRechargePackage tcMemberRechargePackage);

    boolean update(TcMemberRechargePackage tcMemberRechargePackage);

    TcMemberRechargePackage getByPackageId(String packageId);

    List<TcMemberRechargePackage> listEnableAll();

    List<TcMemberRechargePackage> listAllPage(Integer page, Integer pageSize);

    Integer countAll();

}
