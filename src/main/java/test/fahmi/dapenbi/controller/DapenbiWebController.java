package test.fahmi.dapenbi.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import net.andreinc.mockneat.MockNeat;
import test.fahmi.dapenbi.DapenbiApplication;
import test.fahmi.dapenbi.model.Quote;
import test.fahmi.dapenbi.model.User;

@Controller
public class DapenbiWebController {
	private Timer timer = null;

	 @Autowired
	   RestTemplate restTemplate;
	
	
//	@GetMapping("/test")
//	public String index(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
//			Model model) {
//		model.addAttribute("name", name);
//		return "index";
//	}
	
	
	
	
	
	
//	@Bean
//	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
//		return args -> {
//			User user = restTemplate.getForObject(
//					"https://127.0.0.1:8080/user/", User.class);
////			ResponseEntity<User[]> response =
////					  restTemplate.getForEntity(
////					  "http://localhost:8080/user/",
////					  User[].class);
////					User[] user = response.getBody();		
//			DapenbiApplication.log.warn(user.toString());
//		};
//	}
	
	@GetMapping("/")
	public String index(Model model) {
//		User user = restTemplate.getForObject(
//				"https://localhost:8080/user/", User.class);	
		ResponseEntity<List<User>> responseEntity =
		        restTemplate.exchange("http://localhost:8080/user/",
		            HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
		            });
		List<User> listOfUser = responseEntity.getBody();
		DapenbiApplication.log.warn(listOfUser.toString());
		model.addAttribute("users", listOfUser);
		model.addAttribute("stop", true);
	    model.addAttribute("isNotFirst", false);
	    return "index";
	}
	

	
	
	@GetMapping("/generate")
	public String showRegistrationForm(Model model) {
	    model.addAttribute("waktu", new Date());
	     
	    return "index";
	}
	
	@PostMapping("/mulai")
	 public String mulai(Model model) {
		 timer = new Timer();
		 Calendar cal = Calendar.getInstance(); // creates calendar
		 cal.setTime(new Date());               // sets calendar time/date
		 cal.add(Calendar.SECOND, 5);      // adds one hour
		    
		 MockNeat m = MockNeat.threadLocal();
		 final Path path = Paths.get("./dataRandom.csv");
		 List<String> users = new ArrayList<String>();	 
		 timer.schedule( new TimerTask() {
		     public void run() {
		    	 m.fmt("#{id},#{nip},#{name},#{dob},#{gender},#{address},#{idNumber},#{phone}")
		         .param("id", m.regex("\\d{4}"))
		         .param("nip", m.regex("\\d{4}"))
		         .param("name", m.names().full(90))
		         .param("dob", m.localDates())
		         .param("gender", m.genders().letter())
		         .param("address", m.addresses())
		         .param("idNumber", m.regex("\\d{16}"))
		         .param("phone", m.regex("081[7-9]{1}-\\d{4}-\\d{4}"))
		         .list(1)
		         .consume(list -> {
		        	 users.addAll(list);
		        	 System.out.println(list);
		        	 try { Files.write(path, list, StandardOpenOption.CREATE, StandardOpenOption.WRITE); }
		             catch (IOException e) { e.printStackTrace(); }
		         });
		     }
		  }, cal.getTime(), 10*1000);
		 model.addAttribute("waktu", "Cronjob dimulai 3 menit dari waktu sekarang: "+ cal.getTime());
		 model.addAttribute("users", users);
		 model.addAttribute("start", true);
		 model.addAttribute("stop", false);
		    model.addAttribute("isNotFirst", true);
		 return "index";
	 }
	 
	 
	 @PostMapping("/berhenti") 
	 public String stop(Model model){ 
		 if(timer != null) timer.cancel();
		 model.addAttribute("waktu", "Cronjob telah berhenti");
		 model.addAttribute("start", false);
		 model.addAttribute("stop", true);
		    model.addAttribute("isNotFirst", true);
		 return "index";
	  }

}
