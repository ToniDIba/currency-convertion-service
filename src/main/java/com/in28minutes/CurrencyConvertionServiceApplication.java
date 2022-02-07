package com.in28minutes;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.List;


/// https://www.appsdeveloperblog.com/register-web-service-with-eureka-server/


//@ComponentScan("com.example.currencyconvertionservice.repository")
@SpringBootApplication
@EnableFeignClients("com.in28minutes.microservices.repository")
//@EnableFeignClients
@EnableDiscoveryClient


public class CurrencyConvertionServiceApplication  {


	public static void main(String[] args) {




		SpringApplication.run(CurrencyConvertionServiceApplication.class, args);
	}










}
