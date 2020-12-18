package com.projekat.demo.mail;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Part;
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

import com.projekat.demo.dto.ContactDTO;
import com.projekat.demo.dto.EmailDTO;
import com.projekat.demo.dto.MMessageDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Attachment;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Tag;
import com.projekat.demo.service.FolderService;
import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.MailSSLSocketFactory;



@Component
public class Send_Pull_Mails_API {
	private final String TMP_PATH = Objects.requireNonNull(getClass()
            .getClassLoader()
            .getResource(""))
            .getPath().substring(1)
            + "tmp" + File.separator;
	
	@Autowired
	private FolderService folderService;
	
	public boolean sendMessage(MMessage message) {
        String EMAIL_TO = message.getTo();
        String EMAIL_CC = message.getCc();
        String EMAIL_BCC = message.getBcc();
        String EMAIL_FROM = message.getFrom();
 
        Account account = message.getAccount();
        String username = account.getUsername();
        String password = account.getPassword();
        
        // Parametri za mail server
        Properties properties = new Properties();
        properties.put("mail.smtp.socketFactory.port", account.getSmtpPort());
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.host", account.getSmtpAddress());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", account.getSmtpPort());
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.enable", "true");
 
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("lelekrunic1@gmail.com", "pereCvetka!8");
            }
        });
 
        try {
            MimeMessage APImessage = new MimeMessage(session);
 
            APImessage.setFrom(new InternetAddress(EMAIL_FROM));
            APImessage.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO));
            APImessage.setRecipients(javax.mail.Message.RecipientType.CC, InternetAddress.parse(EMAIL_CC));
            APImessage.setRecipients(javax.mail.Message.RecipientType.BCC, InternetAddress.parse(EMAIL_BCC));
            APImessage.setSubject(message.getSubject());
            
            // Postavljanje sadrzaja poruke i attachmenta
            Multipart multipart = new MimeMultipart();

            // Postavljanje teksta
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(message.getContent(), "text/plain");
            multipart.addBodyPart(textPart);

            // Postavljanje attachmenta
            for (Attachment attachment : message.getAttachments()) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                
                attachmentPart.setContent(Base64.decodeBase64(attachment.getData()), attachment.getMimeType());
                attachmentPart.setFileName(attachment.getName());
                
                multipart.addBodyPart(attachmentPart);
            }

            APImessage.setContent(multipart);

            Transport.send(APImessage);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return false;
	}
	
	/**
	 * Pulls new e-mails using JavaMailAPI and returns them
	 * as a List. You can do further operations to those mails.
	 * 
	 * @param Account that is going to be synchronized.
	 * @return List of new e-mails.
	 */
	
	public List<MMessage> loadMessages(Account account) {
		
		ArrayList<MMessage> messages = new ArrayList<MMessage>();
	
		Store store = null;
		javax.mail.Folder APIinbox = null;
		
        try {
            Properties properties = new Properties();
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
  		  	sf.setTrustAllHosts(true);
  		  	properties.put("mail.imaps.ssl.trust", "*");
  		  	properties.put("mail.imaps.ssl.socketFactory", sf);
  		  	properties.setProperty("mail.store.protocol", "imaps");
		  	properties.put("mail.imap.host", account.getInServerAddress());
		  	properties.put("mail.imap.port", account.getInServerPort());
		  	properties.put("mail.imap.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		  	properties.setProperty("mail.imap.socketFactory.fallback", "false");
		  	properties.setProperty("mail.imap.socketFactory.port",
		  	String.valueOf(account.getInServerPort()));
            
            Session session = Session.getDefaultInstance(properties, null);

            store = session.getStore("imaps");
            store.connect("imap.googlemail.com", 993, account.getUsername(), account.getPassword());
            
            APIinbox = store.getFolder("inbox");
            APIinbox.open(javax.mail.Folder.READ_ONLY);
            
            Folder inbox = folderService.findInbox(account);
            
            javax.mail.Message[] APImessages = APIinbox.getMessages();
 
            for (javax.mail.Message APImessage : APImessages) {
            	if (account.getLastSyncTime() == null || APImessage.getSentDate().after(account.getLastSyncTime())) {
		            MMessage message = new MMessage();
		            
		            message.setFrom(APImessage.getFrom()[0].toString());
		
		            Address[] APIto = APImessage.getRecipients(RecipientType.TO);
		            if (APIto != null) {
			            List<EmailDTO> to = new ArrayList<EmailDTO>();
			            for (Address address : APIto) {
			            	to.add(new EmailDTO(address.toString()));
			            }
			            message.setTo(MMessageDTO.recipientsToString(to));
		            } else {
		            	message.setTo(null);
		            }
		            
		            Address[] APIcc = APImessage.getRecipients(RecipientType.CC);
		            if (APIcc != null) {
			            List<EmailDTO> cc = new ArrayList<EmailDTO>();
			            for (Address address : APIcc) {
			            	cc.add(new EmailDTO(address.toString()));
			            }
			            message.setCc(MMessageDTO.recipientsToString(cc));
		            } else {
		            	message.setCc(null);
		            }
		            
		            Address[] APIbcc = APImessage.getRecipients(RecipientType.BCC);
		            if (APIbcc != null) {
			            List<EmailDTO> bcc = new ArrayList<EmailDTO>();
			            for (Address address : APIbcc) {
			            	bcc.add(new EmailDTO(address.toString()));
			            }
			            message.setBcc(MMessageDTO.recipientsToString(bcc));
		            } else {
		            	message.setBcc(null);
		            }
		            
		            Date timestamp = APImessage.getSentDate();
		            message.setDateTime(new Timestamp(timestamp.getTime()));
		            
		            message.setSubject(APImessage.getSubject());
		            
		            //setMessageContent(APImessage, message);
		            
		            message.setTags(new ArrayList<Tag>());
		            
		            message.setFolder(inbox);
		            message.setAccount(account);
		            message.setUnread(true);
		            
		            
		            ArrayList<Attachment> attachments = new ArrayList<Attachment>();
		            
		            MimeMultipart multipart = (MimeMultipart) APImessage.getContent();
		            
		            ArrayList<BodyPart> parts = extractParts(multipart);

		            for (int i = 0; i < parts.size(); i++) {
		                MimeBodyPart bodyPart = (MimeBodyPart) parts.get(i);

		                byte[] bytes;
		                
		                if (bodyPart.isMimeType("text/plain")) {
		                	message.setContent((String) bodyPart.getContent());
		                	continue;
		                } else if (bodyPart.isMimeType("image/*")) {
		                	BASE64DecoderStream base64DecoderStream = (BASE64DecoderStream) bodyPart.getContent();
		                    bytes = IOUtils.toByteArray(base64DecoderStream);
		                } else {
		                	InputStream is = bodyPart.getInputStream();
			                bytes = IOUtils.toByteArray(is);		                	
		                }
		                		                
		                Attachment attachment = new Attachment();
		                attachment.setName(bodyPart.getFileName());
		                attachment.setData(Base64.encodeBase64String(bytes));
		                
		                attachment.setMimeType("image/*");
		                attachment.setMessage(message);
		                
		                attachments.add(attachment);
		            }
		            
		            message.setAttachments(attachments);
		            
		            messages.add(message);
            	}
	        }
        } catch (Exception ex) {
        	ex.printStackTrace();
        } finally {
        	try {
        		APIinbox.close();
        	} catch (Exception ex) {
        		ex.printStackTrace();
        	}
        	
        	try {
        		store.close();
        	} catch (Exception ex) {
        		ex.printStackTrace();
        	}
        }
        
        return messages;
	}
	
	/**
	 * @deprecated Initially used to transfer data from API mail to our entity.
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void setMessageContent(javax.mail.Message APImessage, MMessage message) {
		try {
	        if (APImessage.isMimeType("text/plain"))
	        	message.setContent((String) APImessage.getContent());
	        else if (APImessage.isMimeType("multipart/*")) {
	        	
	        	List<Attachment> messageAttachments = message.getAttachments();
	        	
	        	Multipart multipart = (Multipart) APImessage.getContent();
	        	
	        	StringBuilder textContent = new StringBuilder();
	        	for (int i = 0; i < multipart.getCount(); i++) {
	        		BodyPart part = multipart.getBodyPart(i);
	        		
	        		if (part.isMimeType("text/plain")) {
	    	        	textContent.append((String) part.getContent());
	        		} else if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
	    	        	Attachment attachment = new Attachment();
	    	        	
	    	        	MimeBodyPart bodyPart = (MimeBodyPart) part;
	    	        	
	    	        	String fileName = bodyPart.getFileName();

	    	        	bodyPart.saveFile(TMP_PATH + File.separator + fileName);

                        byte[] bFile = Files.readAllBytes(new File(TMP_PATH + fileName).toPath());
                        String sFile = Base64.encodeBase64String(bFile);

                        attachment.setName(fileName);
                        attachment.setData(sFile);
                        attachment.setMimeType(bodyPart.getContentType().split(";")[0]);

	    	            attachment.setMessage(message);

	    	            messageAttachments.add(attachment);
	    	        }
	        	}

	        	message.setContent(textContent.toString());
	        }
	        	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// Pomocna metoda za izvlacenje attachmenta iz API-jeve poruke
	private ArrayList<BodyPart> extractParts(MimeMultipart multipart) {
		ArrayList<BodyPart> parts = new ArrayList<BodyPart>();
		
		try {
			for (int i = 0; i < multipart.getCount(); i++) {
	            MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(i);
	            if (bodyPart.isMimeType("text/plain")) {
	            	parts.add(bodyPart);
	            } else if (bodyPart.isMimeType("image/*")) {
	            	parts.add(bodyPart);
	            } else if (bodyPart.isMimeType("multipart/*")) {
	            	for (BodyPart part : extractParts((MimeMultipart)bodyPart.getContent())) {
	            		parts.add(part);
	            	}
	            }
	        }
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return parts;
	}
}
