package org.mumu.controller;

import lombok.extern.slf4j.Slf4j;
import org.mumu.pojo.Payment;
import org.mumu.pojo.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("consumer")
@Slf4j
public class PaymentController {

   @Resource
   RestTemplate restTemplate;


    @RequestMapping("findById/{id}")
    public Result<Payment> findById(@PathVariable("id") long id){
        String url = "http://localhost:8001/service-provider/findById?id=" + id; //维护服务提供方的ip+端口
        //如果我们的提供方做了集群，在远程调用服务的时候，我们通过服务提供方的服务名称进行访问即可。(负载均衡的调用)
        // getForObject 基于get请求远程调用服务提供方里面的服务 参数1 服务提供方的url  参数2 响应数据的类型的字节码对象
        Result result = restTemplate.getForObject(url, Result.class);
        return result;
    }

}
