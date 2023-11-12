package org.mumu;

import org.mumu.pojo.Payment;
import org.mumu.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-provider",fallback = PaymentFallBack.class)
public interface PaymentClient {
    @RequestMapping("provider/findById")
    public Result<Payment> findById(@RequestParam("id") long id);
    @RequestMapping("provider/hello")
    public Result<Payment> hello();

    @RequestMapping("provider/testCircuitBreaker")
    public Result<Payment> testCircuitBreaker(@RequestParam("id") long id);
}
