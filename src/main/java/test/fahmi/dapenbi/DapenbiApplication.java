package test.fahmi.dapenbi;

import java.util.logging.FileHandler;
import java.util.logging.Logger;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

import test.fahmi.dapenbi.model.Quote;
import test.fahmi.dapenbi.model.User;
import test.fahmi.dapenbi.utils.CsvFormat;

@SpringBootApplication
@EnableJpaAuditing
public class DapenbiApplication {
	
	public static final Logger log = Logger.getLogger("DAPENBI");
	

	public static void main(String[] args) {
		SpringApplication.run(DapenbiApplication.class, args);
		
		
		log.info(User.data_received + " : Records Received");
		log.info(User.data_successful + " : Records Successful");
		log.info(User.data_failed + " : Records Failed");

		// 2. Log only the 3 fields in a separate file under resources/Result.log
		try {  
//	        FileHandler fileHandler = new FileHandler("src/main/resources/logs/Result.log");  
//	        log.addHandler(fileHandler);
//	        fileHandler.setFormatter(new CsvFormat());
	        log.info(User.data_received + " : Records Received");
	        log.info(User.data_successful + " : Records Successful");
	        log.info(User.data_failed + " : Records Failed");
	    } catch (Exception e) {  
	        e.printStackTrace();}  
		
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	

}
