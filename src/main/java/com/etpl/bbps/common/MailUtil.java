package com.etpl.bbps.common;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.stereotype.Component;


@Component
public class MailUtil {
	
	
public void sendReconMail(List<String> toList, List<String> ccList,File file) throws IOException {		
		
		StringBuffer mailBuffer = new StringBuffer();
		String stb=("<html><head><body> Hi,<br/><br/>"
				+ "Please find the attached Recon file from DRK01(PaybizzIndia.com)."
				+ "</body></html>");
		
		mailBuffer.append(stb);
		String from = "mis@paybizzindia.com";
		String host = "smtp.gmail.com";
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
     Session   session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

               // return new PasswordAuthentication("deepakpanda05@gmail.com", "zrvhxwfzebvdgmxt");
            return new PasswordAuthentication("mis@paybizzindia.com", "qjumjowdmihvdntd");
            }
        });
        session.setDebug(true);
        
        try {
            MimeMessage message = new MimeMessage(session);            
            MimeBodyPart htmlPart = new MimeBodyPart();
            MimeBodyPart attachmentPart = new MimeBodyPart();
            MimeMultipart multipart = new MimeMultipart(); 
            message.setFrom(new InternetAddress(from));
            for(String to:toList)
            {
            System.out.println("toMailRecipint is:::::::"+to);	
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            }
            for(String cc:ccList)
            {
            System.out.println("ccMailRecipint is:::::::"+cc);
            message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
            }
            message.setSubject("Recon File DRK01(PaybizzIndia.com) - ("+CommonUtils.getCurrentDate()+")");
    		
            htmlPart.setDataHandler(new DataHandler(mailBuffer.toString(), "text/html"));
			attachmentPart.attachFile(file);
            multipart.addBodyPart(htmlPart);
            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);
            Transport.send(message);
          System.out.println("mail sent successfully:::::::::::::::::");
            
        } catch (Exception mex) {
        	System.out.println("MessagingException==="+mex);
        }
        		
	}

}
