package com.etpl.bbps;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EtplBbpsApplication {

	@PostConstruct
	public void init(){
	    TimeZone.setDefault(TimeZone.getTimeZone("IST"));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EtplBbpsApplication.class, args);
	}
}
