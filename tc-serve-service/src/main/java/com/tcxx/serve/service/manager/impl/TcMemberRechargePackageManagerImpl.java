package com.tcxx.serve.service.manager.impl;

import com.tcxx.serve.service.entity.TcMemberRechargePackage;
import com.tcxx.serve.service.manager.TcMemberRechargePackageManager;
import com.tcxx.serve.service.mapper.TcMemberRechargePackageMapper;
import com.tcxx.serve.service.query.TcMemberRechargePackageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TcMemberRechargePackageManagerImpl implements TcMemberRechargePackageManager {

    @Autowired
    private TcMemberRechargePackageMapper tcMemberRechargePackageMapper;

    @Override
    public boolean insert(TcMemberRechargePackage tcMemberRechargePackage) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        tcMemberRechargePackage.setPackageId(uuid);
        int i = tcMemberRechargePackageMapper.insert(tcMemberRechargePackage);
        return 1 == i;
    }

    @Override
    public boolean update(TcMemberRechargePackage tcMemberRechargePackage) {
        return 1 == tcMemberRechargePackageMapper.update(tcMemberRechargePackage);
    }

    @Override
    public TcMemberRechargePackage getByPackageId(String packageId) {
        TcMemberRechargePackageQuery query = new TcMemberRechargePackageQuery();
        query.setPackageId(packageId);
        return tcMemberRechargePackageMapper.getByPackageId(query);
    }

    @Override
    public List<TcMemberRechargePackage> listEnableAll() {
        return tcMemberRechargePackageMapper.listEnableAll();
    }

    @Override
    public List<TcMemberRechargePackage> listAllPage(Integer page, Integer pageSize) {
        TcMemberRechargePackageQuery query = new TcMemberRechargePackageQuery();
        query.setPagingPageCurrent(page);
        query.setPagingPageSize(pageSize);
        return tcMemberRechargePackageMapper.listAllPage(query);
    }

    @Override
    public Integer countAll() {
        return tcMemberRechargePackageMapper.countAll();
    }
}
