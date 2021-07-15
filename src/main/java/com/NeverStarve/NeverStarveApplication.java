package com.NeverStarve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication

//開啟@Async註解的自動配置(非同步請求)
@EnableAsync
public class NeverStarveApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(NeverStarveApplication.class, args);
	
	}

}
