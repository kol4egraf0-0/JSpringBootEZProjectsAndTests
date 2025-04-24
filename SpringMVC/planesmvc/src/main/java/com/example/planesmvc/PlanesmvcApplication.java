package com.example.planesmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		exclude = {
				org.springframework.cloud.function.context.config.ContextFunctionCatalogAutoConfiguration.class
		}
)
public class PlanesmvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanesmvcApplication.class, args);
	}

}
