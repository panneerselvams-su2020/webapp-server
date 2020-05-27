package com.cloud.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories("com.cloud.dao")
@EntityScan("com.cloud.model")
@ComponentScan({"com.cloud","com.cloud.service"})
@SpringBootApplication
public class WebappServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebappServerApplication.class, args);
	}

}
