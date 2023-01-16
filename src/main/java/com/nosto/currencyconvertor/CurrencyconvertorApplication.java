package com.nosto.currencyconvertor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication(scanBasePackages = {"com.nosto.currencyconvertor.app"})
@EnableCaching
public class CurrencyconvertorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyconvertorApplication.class, args);
	}

}
