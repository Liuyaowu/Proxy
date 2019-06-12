package com.mobei.dao;

public class BankAccountDao implements IBankAccountDao {
    public void queryAccount(){
        System.out.println("查询账户余额!!!");
    }
    @Override
    public String testParams(String name, int age) {
        System.out.println("参数测试:" + name + " " + age);
        return name;
    }
}
