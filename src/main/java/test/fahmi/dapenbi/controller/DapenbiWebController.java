package test.fahmi.dapenbi.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.andreinc.mockneat.MockNeat;
import test.fahmi.dapenbi.model.User;

@Controller
public class DapenbiWebController {
	private Timer timer = null;
	
//	@GetMapping("/test")
//	public String index(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
//			Model model) {
//		model.addAttribute("name", name);
//		return "index";
//	}
	
	@GetMapping("/")
	public String index(Model model) {
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
		 cal.add(Calendar.SECOND, 30);      // adds one hour
		    
		 MockNeat m = MockNeat.threadLocal();
		 final Path path = Paths.get("./dataRandom.csv");
		 List<String> users = new ArrayList<String>();	 
		 timer.schedule( new TimerTask() {
		     public void run() {
		    	 m.fmt("#{id},#{first},#{last},#{email},#{salary},#{creditCardNum}")
		         .param("id", m.longSeq().start(10).increment(10))
		         .param("first", m.names().first())
		         .param("last", m.names().last())
		         .param("email", m.emails().domain("company.com"))
		         .param("salary", m.ints().range(1000, 5000))
		         .list(1)
		         .consume(list -> {
		        	 users.addAll(list);
		        	 System.out.println(list);
		        	 try { Files.write(path, list, StandardOpenOption.CREATE, StandardOpenOption.WRITE); }
		             catch (IOException e) { e.printStackTrace(); }
		         });
		     }
		  }, cal.getTime(), 5*1000);
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
