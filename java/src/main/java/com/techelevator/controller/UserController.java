package com.techelevator.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.dao.UserSqlDAO;
import com.techelevator.model.User;


@RestController
@CrossOrigin
public class UserController {
	
	private UserSqlDAO UserSqlDAO;
	
	public UserController(UserSqlDAO UserSqlDAO) {
		this.UserSqlDAO = UserSqlDAO;
	}
	
	@RequestMapping(path = "/users", method = RequestMethod.GET)
	public List<User> getUsers() {
		return UserSqlDAO.findAll();
	}
	
	@RequestMapping(path= "/users/{userId}", method = RequestMethod.GET)
	public User getUserByID(@PathVariable Long userId) {
		return UserSqlDAO.getUserById(userId);	
	}
	
	@RequestMapping(path= "/users/find/{username}", method = RequestMethod.GET)
	public User findByUsername(@PathVariable String username) {
		return UserSqlDAO.findByUsername(username);
	}
	
	@RequestMapping(path= "/users/findID/{username}", method = RequestMethod.GET)
	public int findIdByUsername(@PathVariable String username) {
		return UserSqlDAO.findIdByUsername(username);
	}
	
	// wasn't sure how to implement this one
//	@ResponseStatus(HttpStatus.CREATED)
//	@RequestMapping( path = "", method = RequestMethod.POST)
//	public boolean create(@Valid @RequestBody , @PathVariable("") ) {
//	   return UserSqlDAO.create(username, password, role);
//	}

}
