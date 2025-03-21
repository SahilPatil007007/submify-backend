package net.springboot.submify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SubmifyApplication {

	public static void main(String[] args) {
		try{
			SpringApplication.run(SubmifyApplication.class, args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
