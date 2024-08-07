package dev.daryz.translateT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TranslateTApplication {

	private static final Logger log = LoggerFactory.getLogger(TranslateTApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(TranslateTApplication.class, args);
	}

}
