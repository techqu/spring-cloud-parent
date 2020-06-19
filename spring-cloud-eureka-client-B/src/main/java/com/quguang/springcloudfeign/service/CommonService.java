package com.quguang.springcloudfeign.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by 瞿广 on 2020/4/5 0005.
 */
@Service
public class CommonService {

    @Transactional
    public String get(){
        FunctionService fs = (a,b) ->  String.valueOf(a + b);
        FunctionService fs2 = new FunctionService() {
            @Override
            public String get(int a, int b) {
                return null;
            }
        };
        return "";
    }

    public static void main(String[] args) throws Exception{
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        threadPoolExecutor.execute(()-> {
                    try {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }}
                   );
        System.out.println(threadPoolExecutor.toString());
        Thread.sleep(2000);

        threadPoolExecutor.execute(()->System.out.println(Thread.currentThread().getName()));
        System.out.println(threadPoolExecutor.toString());
        System.out.println(threadPoolExecutor.toString());

    }
}
