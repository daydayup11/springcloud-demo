package org.mumu;

import org.mumu.pojo.Payment;
import org.mumu.pojo.Result;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallBack implements PaymentClient {
//    / 给对应的控制器方法进行降级处理
    @Override
    public Result<Payment> findById(long id) {
        return new Result(200,"hystrix整合feign的降级策略，对findById进行降 级",null);
    }
    @Override
    public Result<Payment> hello() {
        return new Result(200,"hystrix整合feign的降级策略，对hello进行降级",null);
    }

    @Override
    public Result<Payment> testCircuitBreaker(long id) {
        return new Result(200,"hystrix整合feign的降级策略，对testCircuitBreaker进行降级",null);
    }

}
