package test.fahmi.dapenbi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

import test.fahmi.dapenbi.model.Quote;

@SpringBootApplication
@EnableJpaAuditing
public class DapenbiApplication {
	
	public static final Logger log = LoggerFactory.getLogger(DapenbiApplication.class);
	

	public static void main(String[] args) {
		SpringApplication.run(DapenbiApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	

}
