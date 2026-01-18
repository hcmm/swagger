package com.jetnuvem.cotacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.jetnuvem"})
public class ApiCotacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCotacaoApplication.class, args);
	}

}
