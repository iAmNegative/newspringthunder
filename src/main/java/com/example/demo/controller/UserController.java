package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Message;
import com.example.demo.entity.Token;
import com.example.demo.entity.User;
import com.example.demo.utility.ChatUtil;

@RestController
public class UserController {
	
	@Autowired
    ChatUtil chatUtil;	
	
	
	@PostMapping(value="/get/user/details")
	   public User getUserMessage(@RequestBody User user) throws IOException
		{
			return chatUtil.getUserDetails(user);
		}
	
	@PostMapping(value="/get/user/profile/Image")
	   public User getUserMessageImage(@RequestBody User user) throws IOException
		{
			return chatUtil.getUserDetailsImage(user);
		}
	
	
}
