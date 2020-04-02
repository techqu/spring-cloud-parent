package com.quguang.springcloudfeign.command;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesThreadPoolDefault;
import com.quguang.springcloudfeign.vo.Product;

public class CommandHelloWorld extends HystrixCommand<Product> {

    private final String name;

    public CommandHelloWorld(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("examplePool"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("commandKey"))
                    .andThreadPoolPropertiesDefaults(HystrixPropertiesThreadPoolDefault.Setter()
                                                                                       .withCoreSize(10)
                                                                                       .withMaxQueueSize(12)
                                                                                       .withQueueSizeRejectionThreshold(18))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                                                                          .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                                                                          .withExecutionTimeoutInMilliseconds(20000)
                                                                          .withExecutionIsolationSemaphoreMaxConcurrentRequests(30))
        );
        this.name = name;
    }

    @Override
    protected Product run() throws Exception {
        //发送http请求
        String response = "Hello " + name + "!";
        System.out.println(response);
        return new Product();
    }

}

