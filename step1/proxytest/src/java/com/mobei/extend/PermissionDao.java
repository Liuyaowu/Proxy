package com.mobei.extend;

import com.mobei.dao.BankAccountDao;

public class PermissionDao extends BankAccountDao {
    @Override
    public void queryAccount() {
        System.out.println("----权限校验----");
        super.queryAccount();
    }
}
