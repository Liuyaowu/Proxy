package com.mobei.extend;

import com.mobei.dao.BankAccountDao;

public class BankAccountLogDao extends BankAccountDao {
    @Override
    public void queryAccount() {
        System.out.println("----  log  ----");
        super.queryAccount();
    }
}
