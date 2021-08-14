package test.fahmi.dapenbi.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.andreinc.mockneat.MockNeat;
import test.fahmi.dapenbi.model.User;
import test.fahmi.dapenbi.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class DapenbiApiController {
	private Timer timer = new Timer();
	
	@Autowired 
	UserRepository userRepository;
	
	 @GetMapping("/") 
	 public List<User> getAll(){ 
		 return userRepository.findAll();
	  }
	 
//	 @GetMapping("/mulai")
//	 public String mulai(Model model) {
//		 Calendar cal = Calendar.getInstance();  
//		 cal.setTime(new Date());              
//		 cal.add(Calendar.SECOND, 30);      
//		    
//		 MockNeat m = MockNeat.threadLocal();
//		 final Path path = Paths.get("./dataRandom.csv");
//		 List<String> users = new ArrayList<String>();	 
//		 timer.schedule( new TimerTask() {
//		     public void run() {
//		    	 m.fmt("#{id},#{first},#{last},#{email},#{salary},#{creditCardNum}")
//		         .param("id", m.longSeq().start(10).increment(10))
//		         .param("first", m.names().first())
//		         .param("last", m.names().last())
//		         .param("email", m.emails().domain("company.com"))
//		         .param("salary", m.ints().range(1000, 5000))
//		         .list(1)
//		         .consume(list -> {
//		        	 users.addAll(list);
//		        	 System.out.println(list);
//		        	 try { Files.write(path, list, StandardOpenOption.CREATE, StandardOpenOption.WRITE); }
//		             catch (IOException e) { e.printStackTrace(); }
//		         });
//		     }
//		  }, cal.getTime(), 5*1000);
//		 model.addAttribute("waktu", cal.getTime());
//		 model.addAttribute("users", users);
//		 return "index";
//	 }
//	 
//	 
//	 @GetMapping("/berhenti") 
//	 public String stop(){ 
//		 timer.cancel();
//		 return "Proses sudah Berhenti";
//	  }
	 
	 
	  
	  
	  @PostMapping("/") 
	  public User tambahuser(@Valid @RequestBody User user) {
	  return userRepository.save(user); 
	  }
	  
	  @PutMapping("/{id}") 
	  public ResponseEntity<User> updateUser(@PathVariable(value="id")Long id, @Valid @RequestBody User detailuser){ 
	  User user = userRepository.findById(id).get(); 
	  if(user == null) return ResponseEntity.notFound().build();
	  user.setNip(detailuser.getNip());
	  user.setName(detailuser.getName());
	  user.setDob(detailuser.getDob());
	  user.setGender(detailuser.getGender());
	  user.setAddress(detailuser.getAddress());
	  user.setIdNumber(detailuser.getIdNumber()); 
	  User updatedUser = userRepository.save(user); 
	  return ResponseEntity.ok(updatedUser); 
	  }
	  
	  @DeleteMapping("/{id}") 
	  public String deleteBuku(@PathVariable (value="id") Long id){ 
	  User user = userRepository.findById(id).get(); String result = "";
	  if(user == null) { 
		  result = "id "+id+" tidak ditemukan"; return result; 
	  }
	  result = "id "+id+" berhasil di hapus"; userRepository.deleteById(id); 
	  return result; 
	  }
	  
	  @GetMapping("/{id}") 
	  public ResponseEntity<User> getBukuById(@PathVariable(value="id") Long id){ 
		  User user = userRepository.findById(id).get(); 
		  if(user == null) return ResponseEntity.notFound().build(); 
		  return ResponseEntity.ok().body(user); 
	}
	  
	  @GetMapping("/sortuser") 
	  public List<User> sortbuku(@RequestParam(value="title")String name){ 
	return userRepository.findByName(name); 
	}
	  
	  @GetMapping("/sortktp/{idNumber}") 
	  public List<User> sortstatus(@PathVariable(value="idNumber") int idNumber){
	  return userRepository.findByIdNumber(idNumber); 
	  }
	 


}
