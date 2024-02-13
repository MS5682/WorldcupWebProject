package com.world.cup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
public class CupApplication {

	public static void main(String[] args) {
		SpringApplication.run(CupApplication.class, args);
	}

}
