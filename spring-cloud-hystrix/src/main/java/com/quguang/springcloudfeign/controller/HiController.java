package com.quguang.springcloudfeign.controller;

import com.quguang.springcloudfeign.command.CommandHelloWorld;
import com.quguang.springcloudfeign.command.ObservableCommandHelloWorld;
import com.quguang.springcloudfeign.service.ServiceAClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {


    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String sayHi(@RequestParam String name) {

        String world = new CommandHelloWorld("World").execute();
        System.out.println(world);

//        new ObservableCommandHelloWorld("World").is.toFuture().get();

        return "";
    }
}