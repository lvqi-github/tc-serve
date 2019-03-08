package com.tcxx.serve.service.impl;

import com.tcxx.serve.service.TcMemberRechargePackageService;
import com.tcxx.serve.service.entity.TcMemberRechargePackage;
import com.tcxx.serve.service.manager.TcMemberRechargePackageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcMemberRechargePackageServiceImpl implements TcMemberRechargePackageService {

    @Autowired
    private TcMemberRechargePackageManager tcMemberRechargePackageManager;


    @Override
    public boolean insert(TcMemberRechargePackage tcMemberRechargePackage) {
        return tcMemberRechargePackageManager.insert(tcMemberRechargePackage);
    }

    @Override
    public boolean update(TcMemberRechargePackage tcMemberRechargePackage) {
        return tcMemberRechargePackageManager.update(tcMemberRechargePackage);
    }

    @Override
    public TcMemberRechargePackage getByPackageId(String packageId) {
        return tcMemberRechargePackageManager.getByPackageId(packageId);
    }

    @Override
    public List<TcMemberRechargePackage> listAll() {
        return tcMemberRechargePackageManager.listAll();
    }

    @Override
    public List<TcMemberRechargePackage> listAllPage(Integer page, Integer pageSize) {
        return tcMemberRechargePackageManager.listAllPage(page, pageSize);
    }

    @Override
    public Integer countAll() {
        return tcMemberRechargePackageManager.countAll();
    }
}
