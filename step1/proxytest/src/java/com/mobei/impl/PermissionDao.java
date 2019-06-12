package com.mobei.impl;

import com.mobei.dao.IBankAccountDao;

public class PermissionDao implements IBankAccountDao {
    private IBankAccountDao dao;
    public PermissionDao(IBankAccountDao dao) {
        this.dao = dao;
    }
    @Override
    public void queryAccount() {
        System.out.println("----权限校验----");
        dao.queryAccount();
    }

    @Override
    public String testParams(String name, int age) {
        return null;
    }
}
