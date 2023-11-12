package org.mumu.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
@Component
public class LoadBalancerImpl implements LoadBalancer {
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private final int getAndIncrement(){
        int expect;
        int update;
        do{
            //volatile保证可见性
            expect = atomicInteger.get();
            update = expect >= Integer.MAX_VALUE ? 0 : expect + 1;
        }while(!atomicInteger.compareAndSet(expect,update));
        //如果当前值和预期值相等就更新，不然就自旋
        System.out.println("第" + update + "访问");
        return update;
    }

    @Override
    public ServiceInstance instance(List<ServiceInstance> instances) {
        return instances.get(getAndIncrement()%instances.size());

    }
}
