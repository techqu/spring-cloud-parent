package com.quguang.springcloudfeign.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.quguang.springcloudfeign.vo.Product;

public class CommandHelloWorld extends HystrixCommand<Product> {

    private final String name;

    public CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
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

