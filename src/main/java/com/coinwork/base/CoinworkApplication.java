package com.coinwork.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.TimeZone;

@SpringBootApplication
public class CoinworkApplication {
 
	
	public static void main(String[] args) {

//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(CoinworkApplication.class, args);
        LocalDateTime now = LocalDateTime.now();
        System.out.println("현재시간 :" + now);
    }

}


