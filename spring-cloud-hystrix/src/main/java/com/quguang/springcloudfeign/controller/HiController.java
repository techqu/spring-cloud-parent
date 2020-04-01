package com.quguang.springcloudfeign.controller;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixObservableCommand;
import com.quguang.springcloudfeign.command.CommandHelloWorld;
import com.quguang.springcloudfeign.command.ObservableCommandHelloWorld;
import com.quguang.springcloudfeign.service.ServiceAClient;
import com.quguang.springcloudfeign.vo.Product;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Observer;

import java.util.concurrent.Future;

@RestController
public class HiController {


    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String sayHi(@RequestParam String name)  throws Exception{
        HystrixCommand<Product> worldCommand = new CommandHelloWorld("World");
        //同步方式
//        String world = worldCommand.execute();
//        System.out.println(world);



        //异步方式：queue 方法立即返回一个future
        Future<Product> queue = worldCommand.queue();
        Thread.sleep(3000);
        if(queue.isDone()){
            Product s = queue.get();
             System.out.println(s);

        }
//        Observable<Product> fWorld = new CommandHelloWorld("World").observe();
        Observable<Product> fWorld = new CommandHelloWorld("World").toObservable();
        fWorld.toBlocking().single();
        return "";
    }


    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String sayHello(@RequestParam String products) {
        String[] split = products.split(",");
        HystrixObservableCommand<Product> getProducts = new ObservableCommandHelloWorld(split);
        Observable<Product> observe = getProducts.observe();
        observe.subscribe(new Observer<Product>() {
            @Override
            public void onCompleted() {
                System.out.println("获取成功");
            }

            @Override
            public void onError(Throwable throwable) {
               throwable.printStackTrace();
            }

            @Override
            public void onNext(Product product) {
                System.out.println(product);

            }
        });


        return "";
    }
}