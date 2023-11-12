package org.mumu.service.impl;


import org.mumu.dao.PaymentDao;
import org.mumu.pojo.Payment;
import org.mumu.service.PaymentService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    PaymentDao paymentDao;

    @Override
    public Payment findById(long id) {
        return paymentDao.findById(id);
    }

    @Override
    public void add(Payment payment) {
        paymentDao.add(payment);
    }

    @Override
    public void save(Payment payment) {
        paymentDao.add(payment);
    }
}
