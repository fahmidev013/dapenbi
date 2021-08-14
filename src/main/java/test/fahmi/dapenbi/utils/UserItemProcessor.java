package test.fahmi.dapenbi.utils;

import org.springframework.batch.item.ItemProcessor;

import lombok.extern.slf4j.Slf4j;
import test.fahmi.dapenbi.model.User;

@Slf4j
public class UserItemProcessor implements ItemProcessor<User, User> {
	 
	  @Override
	  public User process(User User) throws Exception {
	    final String nip = User.getNip().toUpperCase();
	    final String name = User.getName().toUpperCase();
	    final String dob = User.getDob().toUpperCase();
	    final String gender = User.getGender().toUpperCase();
	    final String address = User.getAddress().toUpperCase();
	    final String idNumber = User.getIdNumber().toUpperCase();
	    final String phone = User.getPhone().toUpperCase();
	    final User transformedUser = new User(nip, name, dob, gender, address, idNumber, phone);
//	    log.info("Converting (" + User + ") into (" + transformedUser + ")");
	 
	    return transformedUser;
	  }

	
	}
