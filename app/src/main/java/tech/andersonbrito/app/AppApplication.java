package tech.andersonbrito.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
		LOGGER.info("#######################################################");
		LOGGER.info("Application Multitenant started successfully");
		LOGGER.info("Swagger UI: http://localhost:8080/swagger-ui/index.html");
		LOGGER.info("#######################################################");
	}
}
