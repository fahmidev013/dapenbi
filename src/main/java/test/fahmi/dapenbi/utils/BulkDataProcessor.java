package test.fahmi.dapenbi.utils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.batch.item.ItemProcessor;

import test.fahmi.dapenbi.model.User;

public class BulkDataProcessor implements ItemProcessor<User, User>{

	@Override
	public User process(User User) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("Step 2 - Initial Data :"+User.toString());
		
		// Escape string fields with comma (,) ie. Fields with comma are double-quoted
		// Implemented for image url. Same concept can be extended for other fields by passing getter returns to function below
		
		
		if (User.recordNotComplete()) {
			
			return User;
		}
		
		System.out.println("Step 2 Ends :"+ User.toString());
		
		return null;
		
	} // End of process()
	
} // End of Class
