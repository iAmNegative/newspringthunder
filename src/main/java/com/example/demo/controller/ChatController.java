package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Message;
import com.example.demo.entity.Token;
import com.example.demo.utility.ChatUtil;





@RestController
public class ChatController {

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    ChatUtil chatUtil;	
	
	@RequestMapping(value="/get/all/messages",method=RequestMethod.GET)
   public List<Message> getMessage() throws IOException
	{
		
		return chatUtil.getMessages();
	}
	@PostMapping(value="/get/messages/{user1}/{user2}")
	   public List<Message> getUserMessage(@PathVariable long user1 ,@PathVariable long user2 ,@RequestBody Token user) throws IOException
		{
		logger.info(user.getJwt());
			return chatUtil.getUsersMessage(user1,user2,user.getJwt());
		}
	@RequestMapping(value="/get/all/messages/{jwt}",method=RequestMethod.GET)
	public List<Message> getMessage( @PathVariable String jwt) throws IOException
	{
		
		return chatUtil.getMessages( jwt);
	}
}
