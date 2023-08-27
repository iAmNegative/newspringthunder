package com.example.demo.utility;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Comparator;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.FormatInfo;
import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class ChatUtil {
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	
	public String registerUser(User userVo){
		
		
		
		return null;
		
		
	}




	
	public List<Message> getMessages(String jwt) throws IOException{
		
		
		List<Message> messages = new ArrayList<>();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization","Bearer " +jwt);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		String resp = restTemplate.exchange("http://localhost:1337/api/messages?populate=*",
				HttpMethod.GET,entity,String.class).getBody();
		
		if(resp!=null && !resp.isEmpty()) {
			
			messages = mapJsonToMessages(resp);
		}	
		return messages;
		
		
	}
	
	public  List<Message> mapJsonToMessages(String jsonResponse) throws IOException {
        List<Message> messages = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        JsonNode dataNode = rootNode.get("data");
        if (dataNode != null && dataNode.isArray()) {
            for (JsonNode messageNode : dataNode) {
                long messageId = messageNode.get("id").asLong();
                String userMessage = messageNode.path("attributes").get("userMessage").asText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
//                LocalDateTime createdAt = LocalDateTime.parse(messageNode.path("attributes").get("createdAt").asText(), formatter);
//                LocalDateTime createdAt = LocalDateTime.parse(messageNode.path("attributes").get("createdAt").);
//                LocalDateTime updatedAt = LocalDateTime.parse(messageNode.path("attributes").get("updatedAt"));
//                LocalDateTime updatedAt = LocalDateTime.parse(messageNode.path("attributes").get("updatedAt").asText(), formatter);

                LocalDateTime createdAt = ZonedDateTime.parse(messageNode.path("attributes").get("createdAt").asText().toString(), formatter).toLocalDateTime();
                
                LocalDateTime updatedAt = ZonedDateTime.parse(messageNode.path("attributes").get("updatedAt").asText().toString(), formatter).toLocalDateTime();
                
                
                
        		logger.info(userMessage);

                User senderUser = mapUserFromJson(objectMapper, messageNode.path("attributes").path("senderUser").path("data"));
                User receiverUser = mapUserFromJson(objectMapper, messageNode.path("attributes").path("receiverUser").path("data"));

                Message message = new Message();
                message.setMessageId(messageId);
                message.setUserMessage(userMessage);
                message.setCreatedAt(createdAt);
                message.setUpdatedAt(updatedAt);
                message.setSenderUser(senderUser);
                message.setReceiverUser(receiverUser);

                messages.add(message);
            }
        }

        return messages;
    }

    public  User mapUserFromJson(ObjectMapper objectMapper, JsonNode userNode) throws IOException {
        User user = objectMapper.readValue(userNode.toString(), User.class);
        if(userNode.get("id")!=null) {
        	
        	 long userId = userNode.get("id").asLong();
             String userName = userNode.path("attributes").get("username").asText();
             String firstName = null;
             String lastName  = null;
             if(userNode.path("attributes").get("firstName")!=null) {
            	 
                 firstName = userNode.path("attributes").get("firstName").asText();
 
             }
             if(userNode.path("attributes").get("lastName")!=null) {
            	 
                 lastName = userNode.path("attributes").get("lastName").asText();
 
             }
             String email = userNode.path("attributes").get("email").asText();
             
             user.setUserId(userId);
             user.setFirstName(firstName);
             user.setLastName(lastName);
             user.setUsername(userName);
             user.setEmail(email);
        }
       
        
      
        
        return user;
    }
    public List<Message> getUsersMessage(long user1,long user2,String jwt) throws IOException{
    	
    	List<Message> allMessage = new ArrayList<>();
    	allMessage = getMessages(jwt);
    	List<Message> results = new ArrayList<>();
    	for(Message m : allMessage) {
    		
    		if((m.getReceiverUser().getUserId() == user1 && m.getSenderUser().getUserId() == user2) || (m.getReceiverUser().getUserId() == user2 && m.getSenderUser().getUserId() == user1)) {
    			results.add(m);
    		}		
    	}
    	Comparator<Message> feeComparator  = (c1, c2) -> (int) (c1.getMessageId() - c2.getMessageId());
    	results.sort(feeComparator);
    	
		return results;
    	
    	
    }
    public List<Message> getMessages() throws IOException{
		
		
		List<Message> messages = new ArrayList<>();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		String apiaddress = "https://strapi-deployment-hzpa.onrender.com/api/messages?populate=";
		String resp = restTemplate.exchange(apiaddress+"*",
				HttpMethod.GET,entity,String.class).getBody();
		
		if(resp!=null && !resp.isEmpty()) {
			
			messages = mapJsonToMessages(resp);
		}	
		return messages;
		
		
	}
    
    public User getUserDetails(User user) throws IOException{
		
		
  		List<User> userList = new ArrayList<>();
  		RestTemplate restTemplate = new RestTemplate();
  		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization","Bearer " +user.getJwt());
  		headers.setContentType(MediaType.APPLICATION_JSON);
  		HttpEntity<String> entity = new HttpEntity<>(headers);
  		
  		String apiaddress = "https://strapi-deployment-hzpa.onrender.com/api/users/?populate=";
//  		String apiaddress = "http://localhost:1337/api/users/?populate=";

  		 userList = restTemplate.exchange(apiaddress+"*",
  				HttpMethod.GET,entity,new ParameterizedTypeReference<List<User>>(){}).getBody();
  		
  		if(userList!=null && !userList.isEmpty()) {
  			
  			for(User usr : userList) {
  				
  				 if (usr.getUsername().equals(user.getUsername())) {
  					 
  					user = usr;
  					
  					
  					if(usr.getUserProfile()!=null && !usr.getUserProfile().isEmpty()) {
						
  						if(usr.getUserProfile().get(0).getFormats()!=null && !usr.getUserProfile().get(0).getFormats().isEmpty()) {
  							
  							if(usr.getUserProfile().get(0).getFormats().get("large").getUrl()!=null) {
  								
  								user.setUserProfileLargeUrl(usr.getUserProfile().get(0).getFormats().get("large").getUrl());
//  							  break; // Exit loop if the user is found
  							}
  							if(usr.getUserProfile().get(0).getFormats().get("small").getUrl()!=null) {
  								
  								user.setUserProfileSmallUrl(usr.getUserProfile().get(0).getFormats().get("small").getUrl());
//  							  break; // Exit loop if the user is found
  							}
  							if(usr.getUserProfile().get(0).getFormats().get("medium").getUrl()!=null) {
  								
  								user.setUserProfileMediumUrl(usr.getUserProfile().get(0).getFormats().get("medium").getUrl());
//  							  break; // Exit loop if the user is found
  							}
  							if(usr.getUserProfile().get(0).getFormats().get("thumbnail").getUrl()!=null) {
  								
  								user.setUserProfileThumbnailUrl(usr.getUserProfile().get(0).getFormats().get("thumbnail").getUrl());
//  							  break; // Exit loop if the user is found
  							}
  							
  							
  						}
  						
					
						
					} 
  					
  	                break; // Exit loop if the user is found
  	            }
  								
  			}
  		}	
  		return user;		
  		
  	}





	public User getUserDetailsImage(User user) {
  		List<User> userList = new ArrayList<>();
  		RestTemplate restTemplate = new RestTemplate();
  		HttpHeaders headers = new HttpHeaders();
  		headers.setContentType(MediaType.APPLICATION_JSON);
  		HttpEntity<String> entity = new HttpEntity<>(headers);
  		
  		String apiaddress = "https://strapi-deployment-hzpa.onrender.com/api/user?populate=";
//  		String apiaddress = "http://localhost:1337/api/users/?populate=";

  		 userList = restTemplate.exchange(apiaddress+"*",
  				HttpMethod.GET,entity,new ParameterizedTypeReference<List<User>>(){}).getBody();
  		
  		if(userList!=null && !userList.isEmpty()) {
  			
  			for(User usr : userList) {
  				
  				if(usr.getUsername()!=null && !usr.getUsername().isEmpty()) {
  					
  					if (usr.getUsername().equals(user.getUsername())) {
  	  					 
  						if(usr.getUserProfile()!=null && !usr.getUserProfile().isEmpty()) {
  							
  							if(usr.getUserProfile().get(0).getFormats().get("large").getUrl()!=null) {
  								
  								user.setUserProfileLargeUrl(usr.getUserProfile().get(0).getFormats().get("large").getUrl());
//  							  break; // Exit loop if the user is found
  							}
  							if(usr.getUserProfile().get(0).getFormats().get("small").getUrl()!=null) {
  								
  								user.setUserProfileSmallUrl(usr.getUserProfile().get(0).getFormats().get("small").getUrl());
//  							  break; // Exit loop if the user is found
  							}
  							if(usr.getUserProfile().get(0).getFormats().get("medium").getUrl()!=null) {
  								
  								user.setUserProfileMediumUrl(usr.getUserProfile().get(0).getFormats().get("medium").getUrl());
//  							  break; // Exit loop if the user is found
  							}
  							if(usr.getUserProfile().get(0).getFormats().get("thumbnail").getUrl()!=null) {
  								
  								user.setUserProfileThumbnailUrl(usr.getUserProfile().get(0).getFormats().get("thumbnail").getUrl());
//  							  break; // Exit loop if the user is found
  							}
  							
  						}
  	  	              
  	  	            }
  					
  				}
  				 
  								
  			}
  		}	
  		return user;
	}
    
}
