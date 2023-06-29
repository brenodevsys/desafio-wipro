package com.breno.despesa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class DespesaApplication{


	public static void main(String[] args) {
		SpringApplication.run(DespesaApplication.class, args);
	}


}