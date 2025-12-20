package com.cosmocats.intergalactic_market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class IntergalacticMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntergalacticMarketApplication.class, args);
	}

}
