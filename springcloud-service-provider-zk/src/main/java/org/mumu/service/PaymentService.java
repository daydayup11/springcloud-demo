package org.mumu.service;


import org.mumu.pojo.Payment;

public interface PaymentService {

    public Payment findById(long id);

    void add(Payment payment);

    void save(Payment payment);
}
