package com.apigatewayhms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayHmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayHmsApplication.class, args);
    }

}
