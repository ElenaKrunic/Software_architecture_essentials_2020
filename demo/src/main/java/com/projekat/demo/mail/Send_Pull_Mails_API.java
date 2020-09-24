package com.projekat.demo.mail;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projekat.demo.dto.EmailDTO;
import com.projekat.demo.dto.MMessageDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Attachment;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Tag;
import com.projekat.demo.service.FolderService;
import com.sun.mail.util.BASE64DecoderStream;

@Component
public class Send_Pull_Mails_API {
/*
	@Autowired
	private FolderService folderService; 
	

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
	
public List<MMessage> loadMessages(Account account) {
		
		//za cuvanje i citanje poruka 
		Store store = null; 
		javax.mail.Folder mejlInbox = null; 
		ArrayList<MMessage> messages = new ArrayList<MMessage>(); 
		
		try {
			//imap sluzi za primanje mejla 
	        Properties properties = new Properties();
            properties.put("mail.imap.host", account.getInServerAddress());
            properties.put("mail.imap.port", account.getInServerPort());
            properties.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.setProperty("mail.imap.socketFactory.fallback", "false");
            properties.setProperty("mail.imap.socketFactory.port", String.valueOf(account.getInServerPort()));
            
            Session session = Session.getDefaultInstance(properties, null);
            store = session.getStore("imaps");
            store.connect("imap.googlemail.com", 993, account.getUsername(), account.getPassword());

            mejlInbox = store.getFolder("inbox"); 
            mejlInbox.open(javax.mail.Folder.READ_ONLY); 
            
            //pronadji inboks za dati nalog 
            Folder mojInbox = folderService.findInbox(account);
            javax.mail.Message[] mejlPoruke = mejlInbox.getMessages();
            
            for(javax.mail.Message mejlPoruka : mejlPoruke) {
            	MMessage message = new MMessage();
            	message.setFrom(mejlPoruka.getFrom()[0].toString());
            	Address[] apiTo = mejlPoruka.getRecipients(RecipientType.TO);
            	if(apiTo != null) {
            		List<EmailDTO> to = new ArrayList<EmailDTO>();
            		for(Address address : apiTo) {
            			to.add(new EmailDTO(address.toString()));
            		}
            		
            		message.setTo(MMessageDTO.recipientsToString(to));
            	} else {
            		message.setTo(null);
            	}
            	
            	Address[] apiCc = mejlPoruka.getRecipients(RecipientType.CC);
            	if(apiCc!=null) {
            		List<EmailDTO> cc = new ArrayList<EmailDTO>();
            		for(Address address : apiCc) {
            			cc.add(new EmailDTO(address.toString()));
            		}
            		
            		message.setCc(MMessageDTO.recipientsToString(cc));
            	} else {
            		message.setCc(null);
            		System.out.println("Nema cc u pullu mejla"); 
            	}
            	
            	Address[] apiBcc = mejlPoruka.getRecipients(RecipientType.BCC); 
            	if(apiBcc != null) {
            		List<EmailDTO> bcc = new ArrayList<EmailDTO>();
            		for(Address address : apiBcc) {
            			bcc.add(new EmailDTO(address.toString())); 
            		}
            		
            		message.setBcc(MMessageDTO.recipientsToString(bcc));
            	} else {
            		message.setBcc(null);
            		System.out.println("Nema bcc pri pullovanju");
            	}
            	
            	java.util.Date timeStamp = mejlPoruka.getSentDate();
            	message.setDateTime(new Timestamp(timeStamp.getTime()));
            	message.setSubject(mejlPoruka.getSubject());
            	message.setTags(new ArrayList<Tag>());
            	message.setFolder(mojInbox);
            	message.setAccount(account);
            	message.setUnread(true);
            	
            	//preuzimanje attachmenta 
            	ArrayList<Attachment> attachments = new ArrayList<Attachment>();
            	MimeMultipart mp = (MimeMultipart) mejlPoruka.getContent();
            	//metoda uz pomoc koje cu da izvucem glavni dio mejla 
            	ArrayList<BodyPart> parts = getAttachment(mp);
            	
            	for(int i=0; i<parts.size(); i++) {
            		byte[] bytes; 
            		//kastuj
            		MimeBodyPart tijeloMejla = (MimeBodyPart) parts.get(i);
            		
            		if(tijeloMejla.isMimeType("text/plain")) {
            			message.setContent((String)tijeloMejla.getContent());
            			continue;
            		} else if(tijeloMejla.isMimeType("image/**")) {
            			BASE64DecoderStream dekodiraj = (BASE64DecoderStream) tijeloMejla.getContent();
            			//ovde nastaviti sa radom 
            			bytes= IOUtils.toByteArray(dekodiraj);
            		} else {
            			InputStream is = tijeloMejla.getInputStream(); 
            			bytes = IOUtils.toByteArray(is);
            		}
            		
            		Attachment mojAttachment = new Attachment();
            		mojAttachment.setName(tijeloMejla.getFileName());
            		mojAttachment.setData(Base64.encodeBase64String(bytes));
            		mojAttachment.setMimeType("image/*");
            		mojAttachment.setMessage(message);
            		
            		attachments.add(mojAttachment);
            	}
            	
            	message.setAttachments(attachments);
            	messages.add(message);
            }
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				mejlInbox.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				store.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return messages; 
	}

	private ArrayList<BodyPart> getAttachment(MimeMultipart mp) {
		ArrayList<BodyPart> parts = new ArrayList<BodyPart>();
		try {
			for (int i = 0; i < mp.getCount(); i++) {
	            MimeBodyPart bodyPart = (MimeBodyPart) mp.getBodyPart(i);
	            if (bodyPart.isMimeType("text/plain")) {
	            	parts.add(bodyPart);
	            } else if (bodyPart.isMimeType("image/*")) {
	            	parts.add(bodyPart);
	            } else if (bodyPart.isMimeType("multipart/*")) {
	            	for (BodyPart part : getAttachment((MimeMultipart)bodyPart.getContent())) {
	            		parts.add(part);
	            	}
	            }
	        }
		} catch(Exception e) {
			e.printStackTrace();
		}
		return parts;
	}
	*/
}
