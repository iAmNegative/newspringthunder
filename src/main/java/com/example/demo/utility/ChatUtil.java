package com.example.demo.utility;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;


import org.json.JSONObject; 
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.HttpEntity;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class ChatUtil {
	
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
		
		String resp = restTemplate.exchange("https://strapi-deployment-hzpa.onrender.com/api/messages?populate=*",
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
             String firstName = userNode.path("attributes").get("firstName").asText();
             String lastName = userNode.path("attributes").get("lastName").asText();
             String email = userNode.path("attributes").get("email").asText();
             
             user.setUserId(userId);
             user.setFirstName(firstName);
             user.setLastName(lastName);
             user.setUserName(userName);
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
		
		String resp = restTemplate.exchange("http://localhost:1337/api/messages?populate=*",
				HttpMethod.GET,entity,String.class).getBody();
		
		if(resp!=null && !resp.isEmpty()) {
			
			messages = mapJsonToMessages(resp);
		}	
		return messages;
		
		
	}
    
    
}
