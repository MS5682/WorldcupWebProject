package com.world.cup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class CupApplication {
	//@EnableJpaAuditing 추가
	public static void main(String[] args) {
		SpringApplication.run(CupApplication.class, args);
	}

}
