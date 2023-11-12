package org.mumu.controller;

import org.mumu.pojo.Payment;
import org.mumu.pojo.Result;
import org.mumu.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("provider")
public class PaymentController {
    @Resource
    PaymentService paymentService;
    @Value("${server.port}")
    String port;

    @RequestMapping("findById")
    public Result<Payment> findById(@RequestParam("id") long id){
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Payment payment = paymentService.findById(id);
        return new Result<>(200,"数据查询成功,当前服务端口号是:" + this.port,payment);
    }
}
