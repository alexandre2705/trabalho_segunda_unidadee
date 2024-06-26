package br.com.everdev.dfsappb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DfsAppBApplication {

    public static void main(String[] args) {
        SpringApplication.run(DfsAppBApplication.class, args);
    }
}