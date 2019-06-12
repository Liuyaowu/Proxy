package com.mobei.extend;

public class PermissionAndLogDao extends BankAccountLogDao {
    @Override
    public void queryAccount() {
        System.out.println("-----权限校验----");
        super.queryAccount();
    }
}
