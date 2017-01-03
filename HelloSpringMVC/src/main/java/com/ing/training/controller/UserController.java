package com.ing.training.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ing.training.domain.User;
import com.ing.training.service.UserManagementService;
import com.ing.training.validator.UserDataValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/users")
public class UserController extends WebMvcConfigurerAdapter {
    

	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserManagementService userService;
	
	@Autowired
	UserDataValidator userValidator;
	
	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		binder.addValidators(userValidator);
	} 
	
	
	/** This method creates a new user with firstname, lastname, emailaddress etc..
	 * @param user
	 * @return User and HttpStatus
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User> addUser(@Valid @RequestBody User user, Locale locale) {
		
	    	
		logger.info("user: " +user);
		User userInsert=userService.createUser(user);
		logger.info("user inserted successfully: " +userInsert);
		return new ResponseEntity<>(userInsert, HttpStatus.CREATED);
		
		
	}
	
	/** Get user details by userId
	 * @param id
	 * @return User Details
	 */
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public User getUserById(@PathVariable int id){
		
		/*int i=0;
		if(i==0)
		throw new Exception("Error");*/
		
		return userService.getUserById(id);
		
	}
	
	/** Get user details by userId
	 * @param id
	 * @return User Details
	 */	
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public List<User> listUsers() {
	//	return new ArrayList<User>();
		return userService.listUsers();
		
	}
	
	
	
}
