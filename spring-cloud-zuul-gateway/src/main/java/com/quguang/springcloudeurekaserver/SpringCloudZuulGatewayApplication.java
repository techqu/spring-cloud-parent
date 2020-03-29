package com.quguang.springcloudeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class SpringCloudZuulGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudZuulGatewayApplication.class, args);
	}
}
