package com.etpl.bbps.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationUtil {
    
	  @Value(value="${EMAIL_URL}") 
	  private String mailServiceURL;
	  
		public void sendEmail(EmailNotification email) {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.postForEntity(mailServiceURL, email, email.getClass());
		}
	  
	 
}
