package com.projekat.demo.mail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.projekat.demo.dto.ContactDTO;
import com.projekat.demo.dto.EmailDTO;
import com.projekat.demo.dto.MMessageDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Attachment;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Rule;
import com.projekat.demo.entity.Tag;
import com.projekat.demo.service.FolderService;
import com.projekat.demo.service.MessageService;
import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.MailSSLSocketFactory;

import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/**
 * Izvori za javaMail Api : 
 *  https://www.javatpoint.com/java-mail-api-tutorial
 * 	https://www.codejava.net/* 
 *  https://www.tutorialspoint.com/javamail_api/index.htm
 * @author KrunicElena 
 *
 */
@Component
public class MailAPI {
	
	/*
	private final String TMP_PATH = Objects.requireNonNull(getClass()
            .getClassLoader()
            .getResource(""))
            .getPath().substring(1)
            + "tmp" + File.separator;
	
	@Autowired
	private FolderService folderService;
	
	/**
	 * 
	 * @param account povezuje nalog i korisnika 
	 * @return
	 */
	public boolean connectAccountToUser(Account account) {
		
		String protocol = ""; 
		
		if(account.getInServerType() == 0) {
			protocol = "imap"; 
		} else if(account.getInServerType() == 1) {
			protocol = "pop3"; 
		}
		
	try {
	        Properties properties = new Properties();
	    	properties.put("mail.store.protocol", protocol);
	        properties.put("mail.imap.host", account.getInServerAddress());
	        properties.put("mail.imap.port", account.getInServerPort());
	        properties.put("mail.imap.starttls.enable", "true");
	    	Session session = Session.getDefaultInstance(properties);
	    	Store store = session.getStore(protocol + "s");

	        store.connect(account.getInServerAddress(), account.getUsername(), account.getPassword());
	        return true;
        
		} catch (NoSuchProviderException ex) {
			System.out.println("No provider for protocol: " + protocol);
			ex.printStackTrace();
			return false;
		} catch (MessagingException ex) {
			System.out.println("Could not connect to the message store");
			ex.printStackTrace();
			return false;
		}
	}
	/*
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
	/*
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

	public boolean sendMessage(MMessage message)  {
		JavaMailSenderImpl mailSender = getJavaMailSender(message.getAccount());
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mimeMessage,true);
			
			helper.setFrom(message.getFrom());
			helper.setSubject(message.getSubject());
			helper.setText(message.getContent());
			helper.setCc(message.getCc());
			helper.setTo(message.getTo());
			helper.setBcc(message.getBcc());
			//setovati to,bcc,cc
			
			mailSender.send(mimeMessage);
			
			return true;
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	private static JavaMailSenderImpl getJavaMailSender(Account account) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(account.getSmtpAddress());
		mailSender.setPort(account.getSmtpPort());
		
		mailSender.setUsername(account.getUsername());
		mailSender.setPassword(account.getPassword());
		
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");
		
		return mailSender;
	}

	/*
	public static void executeRules(ArrayList<Rule> rules, MessageService messageService) {
		if (rules.size() > 0) {
			List<MMessage> messages = new ArrayList<MMessage>();
			//messages.addAll(rules.get(0).getDestination().getAccount().getMessages());
			messages.addAll(rules.get(0).getFolder().getAccount().getMessages());
			for (MMessage message : messages) {
				for (Rule rule : rules) {
					MMessage m = rule.doRule(message);
					if (m != null && m.getAccount() == null) {
						messageService.remove(message.getId());
					}
					else if (m != null) {
						message = messageService.save(message);
					}
				}
			}
		}
	}
	*/
	
	/*
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
	
	*/
	
	/*
	
	private List<MMessage> downloadFromEmail(String protocol, String host, String port, String username,
			String password, MMessage lastMessage, Account account, Folder folder) throws IOException {
		List<MMessage> messages = new ArrayList<MMessage>();
		
		try {
			  Properties properties = new Properties();
	        	properties.put("mail.store.protocol", protocol);
	            properties.put("mail.imap.host", host);
	            properties.put("mail.imap.port", port);
	            properties.put("mail.imap.starttls.enable", "true");
	        	Session session = Session.getDefaultInstance(properties);
	        	Store store = session.getStore(protocol + "s");

	            store.connect(host, username, password);
			
	            // opens the inbox folder
	            javax.mail.Folder folderInbox = store.getFolder("INBOX");
	            folderInbox.open(javax.mail.Folder.READ_ONLY);
	            
	            // fetches new messages from server
	            
	            int n=folderInbox.getMessageCount();
	            //SearchTerm newerThan = new ReceivedDateTerm(ComparisonTerm.GT, yourDate);
	            
	            Message[] messagesFromInbox = folderInbox.getMessages();
	            
	            if(lastMessage != null) {
	            	System.out.println("ucitava samo nove poruke koje nisu u bazi");
	            	//ima poruka u bazi za ovaj account, ucitaj nove i sacuvaj nove u bazu
	            	List<Message> newMessages = new ArrayList<Message>();
	                for (Message message : messagesFromInbox) {
	                    if (message.getSentDate().after(lastMessage.getDateTime()) && message.getSentDate().before(new Date()))
	                       {
	                    		System.out.println("poruka sa servera vrijeme: " + message.getSentDate() + " " + "poruka iz baze zadnja vrijeme: " + lastMessage.getDateTime());
	                          System.out.println("Subject poruke koja je nakon datuma poslednje poruke iz baze: " + message.getSubject());
	                          newMessages.add(message);
	                          
	                       } 
	                }
	                messages = setMessages(newMessages, account, folder, protocol);
	            	
	            }else {
	            	System.out.println("cita sve, nema poruka u bazi za ovaj account");
	            	//nema ni jedna poruka u bazi za ovaj account, ucitaj sve i sacuvaj ih u bazu
	            	List<Message> lastMessagesAll = new ArrayList<Message>();
	                for (Message message : messagesFromInbox) {
	                	lastMessagesAll.add(message);
	                }
	                //System.out.println(messages.length);
	                //System.out.println(lastMessagesAll.size());
	                messages = setMessages(lastMessagesAll, account, folder, protocol);
	            }
	 
	            // disconnect
	            folderInbox.close(false);
	            store.close();
	        } catch (NoSuchProviderException ex) {
	            System.out.println("No provider for protocol: " + protocol);
	            ex.printStackTrace();
	        } catch (MessagingException ex) {
	            System.out.println("Could not connect to the message store");
	            ex.printStackTrace();
	            return null;
	        }
		
		return messages;
	}
	
	
	private List<MMessage> setMessages(List<Message> newMessages, Account account, Folder folder, String protocol) throws MessagingException, IOException {
		List<MMessage> messages = new ArrayList<MMessage>(); 
		
		 for (int i = 0; i < newMessages.size(); i++) {
	            Message msg = newMessages.get(i);
	            if(protocol.equalsIgnoreCase("imap")) {
	            	if(msg.getFrom() == null || msg.getSentDate() == null) {
	                	continue;
	                }
	            }
	            MMessage message = new MMessage();
	            Address[] froms = msg.getFrom();
	            String from = froms == null ? null : ((InternetAddress) froms[0]).getAddress();
	            //System.out.println(fromAddress);
	            //String from = fromAddress[0].toString();
	            //System.out.println(from);
	            String subject = msg.getSubject();
	            Set<String> toList = parseAddresses(msg
	                    .getRecipients(RecipientType.TO));
	            Set<String> ccList = parseAddresses(msg
	                    .getRecipients(RecipientType.CC));
	            Set<String> bccList = parseAddresses(msg.getRecipients(RecipientType.BCC));

	            //String contentType = msg.getContentType();
	            //System.out.println(contentType);

	           
	            
	            message.setId(-1);
	            if(protocol.equalsIgnoreCase("imap")) {
	            	 message.setUnread(!msg.isSet(Flags.Flag.SEEN));
	                 //System.out.println("imap procitana: " + !msg.isSet(Flags.Flag.SEEN));
	            }else if(protocol.equalsIgnoreCase("pop3")) {
	            	message.setUnread(true);
	                //System.out.println("pop3 procitana: " + msg.isSet(Flags.Flag.SEEN));
	            }
	           
	            message.setFrom(from);
	            message.setTo(toList);
	            message.setCc(ccList);
	            message.setBcc(bccList);
	            message.setDateTime(msg.getSentDate());
	            //System.out.println(msg.getSentDate());
	            message.setSubject(subject);
	            //System.out.println(MailHelper.getTextFromMessage(msg).trim());
	            message.setContent(getTextFromMessage(msg).trim());
	            //System.out.println(message.getContent());
	            Set<Attachment> attachments = extractAttachments(msg);
	            for(Attachment attachment : attachments) {
	            	attachment.setMessage(message);
	            	//System.out.println(attachment.getMimeType());
	            }
	            message.setAttachments(attachments);
	            message.setAccount(account);
	            message.setFolder(folder);
	            
	            messages.add(message);
	        }
		
		
		return messages;
	}

	private Set<Attachment> extractAttachments(Message msg) throws IOException, MessagingException {
	    Set<Attachment> attachments = new HashSet<Attachment>();
        String contentType = msg.getContentType();
        if(!(contentType.contains("multipart"))) {
        	//no attachments
        	return attachments;
        }
        
        Multipart mp = (Multipart) msg.getContent();
        
        for(int i = 0; i<mp.getCount(); i++) {
        	BodyPart bp = mp.getBodyPart(i);
        	if(!Part.ATTACHMENT.equalsIgnoreCase(bp.getDisposition()) && StringUtils.isEmpty(bp.getFileName())) {
        		continue;
        	}
        	
        	//System.out.println("Attachment: " + bp.getFileName());
        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        	
        	int bytesRead;
        	byte[] buff = new byte[65536];
        	InputStream is = bp.getInputStream();
        	while((bytesRead = is.read(buff, 0, buff.length)) != -1) {
        		baos.write(buff, 0, bytesRead);
        	}
        	
        	byte[] data = baos.toByteArray();
        	baos.close();
        	System.out.println("Velicina fajla: " + data.length);
        
        	Attachment attachment = new Attachment();
        	String decodedFilename = MimeUtility.decodeText(bp.getFileName());
        	attachment.setName(decodedFilename);
        	String[] lista = bp.getContentType().split(";");
        	String mimeType = lista[0];
        	attachment.setMimeType(mimeType.toLowerCase() + ";");
        	attachment.setData(java.util.Base64.getEncoder().encodeToString(data));
        	attachments.add(attachment);

        }
        
        return attachments;
	}

	private String getTextFromMessage(Message msg) throws MessagingException, IOException {

    	String s = "";
        if (msg.isMimeType("text/plain")) {
            s = msg.getContent().toString();
        }else if(msg.isMimeType("multipart/*")){
        		MimeMultipart mp = (MimeMultipart) msg.getContent();
        		s = getTextFromMimeMultipart(mp);
        	
        }
      
        return s;
	}

	private static String getTextFromMimeMultipart(MimeMultipart mp) throws MessagingException, IOException {
		int count = mp.getCount();
    	if(count == 0) {
    		throw new MessagingException("Meultipart with no body parts not supported");
    	}
    	boolean mpAlternative = new ContentType(mp.getContentType()).match("multipart/alternative");
    	if(mpAlternative) {
    		return getTextFromBodyPart(mp.getBodyPart(count - 1));
    	}
    	String s = "";
    	for(int i = 0; i < count; i++) {
    		BodyPart bp = mp.getBodyPart(i);
    		s+= getTextFromBodyPart(bp);
    	}
    	return s;
	}
	  private static String getTextFromBodyPart(BodyPart bp) throws MessagingException, IOException{
	    	String s = "";
	    	if(bp.isMimeType("text/plain")) {
	    		s = (String) bp.getContent();
	    	}else if(bp.isMimeType("text/html")) {
	    		String html = (String) bp.getContent();
	    		s = Jsoup.parse(html).wholeText();
	    	}else if(bp.getContent() instanceof MimeMultipart){
	    		s = getTextFromMimeMultipart((MimeMultipart) bp.getContent());
	    	}
	    	return s;
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

	public List<MMessage> main(Account account, Folder folder, MMessage lastMessage) throws IOException {
		String protocol = ""; 
		
		if(account.getInServerType() == 0) {
			protocol = "imap"; 
		} else {
			protocol = "pop3";
		}
		
		String host = account.getInServerAddress(); 
		//String port = account.getInServerPort();
		String port = String.valueOf(account.getInServerPort());
		String username = account.getUsername(); 
		String password = account.getPassword(); 
		
		List<MMessage> messages  = downloadFromEmail(protocol,host,port,username,password,lastMessage,account,folder);
		return messages; 
	}	
	
	*/
}
