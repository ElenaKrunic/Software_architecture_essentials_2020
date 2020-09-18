package javamail;

import java.util.Properties;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.management.RuntimeErrorException;

import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Attachment;
import com.projekat.demo.entity.MMessage;

@Component
public class SendMail {
	
	//public static String myeMail = "lelekrunic@gmail.com";
    //public static String myUsername = "Lele";
    //public static String myPassword = "rarasosa3!";
	
	
	public boolean send(MMessage message) {
		//inicijalizacija parametara
		Account account; 
		String emailTo = message.getTo();
		String emailCc = message.getCc(); 
		String emailBcc = message.getBcc(); 
		String emailFrom = message.getFrom(); 
		account=message.getAccount(); 
		String accUsername = account.getUsername(); 
		String accPass = account.getPassword(); 
		
		Properties props = new Properties();
		props.put("mail.smtp.host",account.getSmtpAddress());
	    props.put("mail.smtp.socketFactory.port",account.getSmtpPort());
	    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.port",account.getSmtpPort());
        props.put("mail.smtp.starttls.enable", "true");
		
        Session sess = Session.getInstance(props, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("lelekrunic@gmail.com", "rarasosa3!");
            }
        });
		
        try {
        	MimeMessage mimeMess = new MimeMessage(sess);
        	
            // Set From: header field of the header.
            mimeMess.setFrom(new InternetAddress(emailFrom));
         
            if(!message.getTo().equals("")) {
                // Set To: header field of the header.
                mimeMess.setRecipients(javax.mail.Message.RecipientType.TO,
                           InternetAddress.parse(message.getTo()));
            }
         
            if(!message.getCc().equals("")) {
            	 mimeMess.setRecipients(javax.mail.Message.RecipientType.CC,
                         InternetAddress.parse(message.getCc()));
            }
            
            if(!message.getBcc().equals("")) {
            	 mimeMess.setRecipients(javax.mail.Message.RecipientType.BCC,
                         InternetAddress.parse(message.getBcc()));
            }
            mimeMess.setSubject(message.getSubject());
            
            //postavljanje text + attachments 
            Multipart mp = new MimeMultipart();
            MimeBodyPart body = new MimeBodyPart();
            body.setContent(message.getContent(), "text/plain");
            mp.addBodyPart(body);
            
            for(Attachment attachment : message.getAttachments()) {
            	MimeBodyPart attach = new MimeBodyPart();
            	String dataForAttachment = attachment.getData(); 
            	String mimeType = attachment.getMimeType();
          
            	attach.setContent(Base64.decodeBase64(dataForAttachment), mimeType);
            	attach.setFileName(attachment.getName());
            	
            	mp.addBodyPart(attach);
            }
            
            //za attachment 
            mimeMess.setContent(mp);
            //za obicnu 
            //mimeMess.setText(message.getContent());
            Transport.send(mimeMess);
           
            System.out.println("Sent message successfully....");
        	return true; 
        } catch(Exception e) {
        	e.printStackTrace();
        	//throw new RuntimeErrorException(e);
        }
    	return false;
	}
}

