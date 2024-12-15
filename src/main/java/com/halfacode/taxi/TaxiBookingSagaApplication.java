package com.halfacode.taxi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.halfacode.taxi"}) // Adjust this to include KafkaProducerConfig's package

public class TaxiBookingSagaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxiBookingSagaApplication.class, args);
	}

}
