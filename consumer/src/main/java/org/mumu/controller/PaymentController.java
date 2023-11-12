package org.mumu.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import lombok.extern.slf4j.Slf4j;
import org.mumu.PaymentClient;
import org.mumu.config.LoadBalancer;
import org.mumu.pojo.Payment;
import org.mumu.pojo.Result;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("consumer")
@Slf4j
//@DefaultProperties(defaultFallback = "callBackHello")
public class PaymentController {

   @Resource
   RestTemplate restTemplate;
   @Resource
   LoadBalancer loadBalancer;
   @Resource
    PaymentClient paymentClient;


    @RequestMapping("findById/{id}")
    public Result<Payment> findById(@PathVariable("id") long id){
//        String url = "http://localhost:8001/provider/findById?id=" + id; //维护服务提供方的ip+端口
        //如果我们的提供方做了集群，在远程调用服务的时候，我们通过服务提供方的服务名称进行访问即可。(负载均衡的调用)
          //String url = "http://SERVICE-PROVIDER/provider/findById?id=" + id ;
        // getForObject 基于get请求远程调用服务提供方里面的服务 参数1 服务提供方的url  参数2 响应数据的类型的字节码对象
//        Result result = restTemplate.getForObject(url, Result.class);
//        Result result = peymentFeignClient.findById(id);
        Result<Payment> result = paymentClient.findById(id);
        return result;
    }


//    @RequestMapping("add")
//    public Result<Payment> add(){
//        Payment payment = new Payment();
//        payment.setSerial("google");
//        Result result = peymentFeignClient.save(payment);
//        return result;
//    }
//
//    @Resource
//    DiscoveryClient discoveryClient;
//
//    //获取注册中心注册的服务实例信息
//    @RequestMapping("discovery")
//    public Object discovery(){
//        // 获取注册中心中服务实例的服务名称
//        List<String> services = discoveryClient.getServices();
//        for(String service : services){
//            log.info("service:" + service);
//        }
//        //根据服务名称获取对应的服务实例信息
//        List<ServiceInstance> instances =
//                discoveryClient.getInstances("service-provider");
//        for(ServiceInstance instance : instances){
//            String host = instance.getHost();//获取服务的主机地址
//            int port = instance.getPort(); //获取服务的端口号
//            String serviceId = instance.getServiceId();//获取微服务名称
//            log.info("host:" + host + " port:" + port + " serviceId:" + serviceId);
//        }
//        return this.discoveryClient;
//    }
    @Resource
    private DiscoveryClient discoveryClient;

    @RequestMapping("testLoadBanlance/{id}")
    public Result<Payment> testLoadBanlance(@PathVariable("id") Long id){
        List<ServiceInstance> instances = discoveryClient.getInstances("SERVICE-PROVIDER");
        if(instances == null || instances.size() <= 0){
            return null;
        }
        ServiceInstance instance = loadBalancer.instance(instances);
        if (instance == null){
            return new Result<>(500,"无服务",null);
        }
//        URI uri = instance.getUri();
//        String url = uri + "/provider/findById?id=" + id;
//        Result result = restTemplate.getForObject(uri + "/provider/findById?id=" + id, Result.class);
        String host = instance.getHost();
        int port = instance.getPort();

        // 构造完整的URL
        String url = "http://" + host + ":" + port + "/provider/findById?id=" + id;

        Result result = restTemplate.getForObject(url, Result.class);
        return result;
    }


    //测试Hystrix熔断降级功能
    @RequestMapping("hello")
//    @HystrixCommand(fallbackMethod = "callBackHello",commandProperties = {@HystrixProperty(
//            name = "execution.isolation.thread.timeoutInMilliseconds",value = "2500"
//    )})
    public Result<Payment> hello(){
        return paymentClient.hello();
    }

    public Result<Payment> callBackHello(){
        return new Result(200,"findById方法触发了局部的降级策略(消费方触发)",null);
    }


    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000"),
            @HystrixProperty(name="circuitBreaker.enabled", value="true"), // 是否开启断 路器
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),// 请求次数
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="10000"), // 时间窗口期
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="60"), //失败率达到多少后跳闸
    })
    @RequestMapping("testCircuitBreaker")
    public Result<Payment> testCircuitBreaker(@RequestParam("id") long id){
        return paymentClient.testCircuitBreaker(id);
    }
}
