package com.quguang.springcloudfeign.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class BeanTest {
    /*
       默认在不指定的时候这个bean的名字就是 getBean
       如果需要指定一下名字就可以
       @Bean("beanlalal")
    */
    @Bean
    public BeanTest getBean(){
        BeanTest bean = new  BeanTest();
        System.out.println("调用方法："+bean);
        return bean;
    }

}


