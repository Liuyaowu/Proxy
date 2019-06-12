package com.mobei.test;

import com.mobei.dao.BankAccountDao;
import com.mobei.dao.IBankAccountDao;
import com.mobei.dao.ITestOther;
import com.mobei.dao.TestOtherImpl;
import com.mobei.impl.BankAccountLogDao;
import com.mobei.impl.PermissionDao;
import com.mobei.utils.ProxyUtil;

public class Test {
public static void main(String[] args) {
//    BankAccountDao dao = new BankAccountDao();
//    dao.queryAccount();

//    BankAccountDao dao = new BankAccountLogDao();
//    dao.queryAccount();

//    BankAccountDao dao = new PermissionAndLogDao();
//    dao.queryAccount();

//    IBankAccountDao dao = new BankAccountLogDao(new BankAccountDao());
//    dao.queryAccount();

//    IBankAccountDao target = new PermissionDao(new BankAccountDao());
//    IBankAccountDao proxy = new BankAccountLogDao(target);
//    proxy.queryAccount();

//    IBankAccountDao dao = (IBankAccountDao) ProxyUtil.newInstance(new BankAccountDao());
//    dao.queryAccount();
//    dao.testParams("张三", 18);


    ITestOther dao = (ITestOther) ProxyUtil.newInstance(new TestOtherImpl());
    dao.test();
}
}
