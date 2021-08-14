package test.fahmi.dapenbi.utils;

import org.springframework.batch.item.ItemProcessor;

import test.fahmi.dapenbi.model.User;

public class UserItemProcessor implements ItemProcessor<User, User>{

	@Override
	public User process(User User) throws Exception {
		// TODO Auto-generated method stub
		
		//Track total records
		User.data_received ++;
		
		//Check if record is incomplete
		//Filter out incomplete records before any processing
		if (User.recordNotComplete()) {
			
			User.data_failed ++;
			return null;
		}
		
		
		//Initial CSV data
		System.out.println("Step 1 - Initial Data: "+User.toString());
		
		
		// New instance of class created with same values for processing
		User u_new = new User(User);
		
		//Convert string to boolean
//		u_new.setB1_out(Boolean.parseBoolean(User.getB1()));
		
		// Convert Base64 encoded string to byte array for efficient storage in DB
		// Better to store image data as byte array than Base64 encoded
		// Byte array can be easily encoded to Base64 for retrieval
//		String image_url = User.getImage();
//		String image_base64;
		// Try-catch block essential for ArrayIndexOutofBoundsException in case the input doesn't contain comma, 
		// is blank or incorrect format
	
		
		// Final Processed data
		System.out.println("Final Data...");
		System.out.println("Values : "+u_new.toString());
		
		User.data_successful ++;
		
		// Return Modified instance of User to writer
		return u_new;
	}
	

}
