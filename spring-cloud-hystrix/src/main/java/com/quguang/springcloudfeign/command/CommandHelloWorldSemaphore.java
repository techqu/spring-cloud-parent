package com.quguang.springcloudfeign.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.quguang.springcloudfeign.vo.Product;

/**
 * 基于信号量的资源隔离方式
 */
public class CommandHelloWorldSemaphore extends HystrixCommand<Product> {

    private final String name;

    public CommandHelloWorldSemaphore(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                                                                          .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));
        this.name = name;
    }

    @Override
    protected Product  run() throws Exception{
        //发送http请求
        String response = "Hello " + name + "!";
        System.out.println(response);
        return new Product();
    }

}

