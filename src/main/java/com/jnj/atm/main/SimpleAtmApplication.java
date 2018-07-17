package com.jnj.atm.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author BALU RAMAMOORTHY
 *
 */
@SpringBootApplication(scanBasePackages = { "com.jnj" })
public class SimpleAtmApplication {
	/**
	 * This is the main method which starts the Spring Boot ATM Application.
	 * 
	 * @param args
	 *            Arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(SimpleAtmApplication.class, args);
	}
}
