package org.mumu.dao;

import org.mumu.pojo.Payment;

public interface PaymentDao {

    //根据id查询payment信息
    public Payment findById(long id);

    //新增payment信息
    public void add(Payment payment);
}
