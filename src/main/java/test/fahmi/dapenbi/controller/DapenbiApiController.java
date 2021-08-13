package test.fahmi.dapenbi.controller;

import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import test.fahmi.dapenbi.model.User;
import test.fahmi.dapenbi.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class DapenbiApiController {
	
	@Autowired 
	UserRepository userRepository;
	
	 @GetMapping("/") 
	 public List<User> getAll(){ 
		 return userRepository.findAll();
	  }
	  
	  
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
