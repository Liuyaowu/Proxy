package com.mobei.impl;
import com.mobei.dao.IBankAccountDao;
public class BankAccountLogDao implements IBankAccountDao {
    private IBankAccountDao dao;
    public BankAccountLogDao(IBankAccountDao dao) {
        this.dao = dao;
    }
    @Override
    public void queryAccount() {
        System.out.println("----log----");
        dao.queryAccount();
    }

    @Override
    public String testParams(String name, int age) {
        return null;
    }
}
