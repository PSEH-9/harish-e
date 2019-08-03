package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.example.demo.util.GetHttpResponse;

@SpringBootApplication
public class BitCoinResultApplication {

	@Autowired
	GetHttpResponse getBPIData;
	
	
	public static void main(String[] args) {
		SpringApplication.run(BitCoinResultApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void readData() {
		try {
			getBPIData.fetchFullResult();
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
