package com.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ClientApplication {
    public static void main(String[] args) throws InterruptedException {
//        NettyClient.sendMessage();
        SpringApplication.run(ClientApplication.class, args);
    }
}
