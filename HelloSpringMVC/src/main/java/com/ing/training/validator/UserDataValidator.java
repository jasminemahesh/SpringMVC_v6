package com.ing.training.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ing.training.domain.User;

@Component
public class UserDataValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user =(User)target;
		
		String email=user.getEmail();
		
		if(email!=null && email.indexOf('@')==-1)
		{
			errors.rejectValue("email", "error.invalid.email");
				
		}
		
	}
	
	

}
