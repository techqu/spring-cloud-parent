package com.quguang.springcloudfeign.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
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
                                                                                       .withQueueSizeRejectionThreshold(5))
        );
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

