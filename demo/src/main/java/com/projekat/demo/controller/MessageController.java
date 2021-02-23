package com.projekat.demo.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.Message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.demo.dto.AttachmentDTO;
import com.projekat.demo.dto.EmailDTO;
import com.projekat.demo.dto.FilterDTO;
import com.projekat.demo.dto.MMessageDTO;
import com.projekat.demo.dto.TagDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Attachment;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Rule;
import com.projekat.demo.entity.Tag;
import com.projekat.demo.entity.User;
import com.projekat.demo.mail.MailAPI;
import com.projekat.demo.repository.AttachmentRepository;
import com.projekat.demo.repository.FolderRepository;
import com.projekat.demo.service.AccountService;
import com.projekat.demo.service.AttachmentService;
import com.projekat.demo.service.ContactService;
import com.projekat.demo.service.FolderService;
import com.projekat.demo.service.MessageService;
import com.projekat.demo.service.MessageServiceInterface;
import com.projekat.demo.service.TagService;
import com.projekat.demo.service.UserService;
import com.projekat.demo.util.Base64;
import com.projekat.demo.util.FilesUtil;


/**
 * Problem: ne ucitava mi se account kao json objekat u porukama 
 * @author lenovo
 *
 */
@RestController
@RequestMapping("api/messages")
public class MessageController {
	
    private static final Logger LOGGER = LogManager.getLogger(MessageController.class);

	
	@Autowired
	private MessageServiceInterface messageServiceInt; 
	
	@Autowired
	private MessageService messageService; 
	
	@Autowired
	private TagService tagService; 
	
	@Autowired
	private AttachmentService attachmentService; 
	
	@Autowired
	private AttachmentRepository attachmentRepository;
	
	@Autowired
	private AccountService accountService; 
	
	@Autowired
	private MailAPI mailApi;
	
	@Autowired
	private FolderRepository folderRepository;
	
	@Autowired
	private FolderService folderService; 
	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private ContactService contactService; 
	
	
	
	/**
	 * 
	 * @return vraca sve poruke iz baze 
	 */
	@GetMapping

	public ResponseEntity<List<MMessageDTO>> getMessages() {
		
		List<MMessage> messages = messageServiceInt.findAll(); 
		
		List<MMessageDTO> dtoMessages = new ArrayList<MMessageDTO>();
		
		for(MMessage message : messages) {
			dtoMessages.add(new MMessageDTO(message));
		}
		
		LOGGER.info("Uspjesno vracena lista poruka"); 

		return new ResponseEntity<List<MMessageDTO>>(dtoMessages, HttpStatus.OK);
	}
	/**
	 * 
	 * @param id proslijedjen id poruke 
	 * @return vraca poruku koja ima odredjeni ID 
	 */
	
	@GetMapping(value="/{id}")
	public ResponseEntity<MMessageDTO> getMessage(@PathVariable("id") Integer id) {
		MMessage message = messageServiceInt.findOne(id);
		
		if(message == null) {
			return new ResponseEntity<MMessageDTO>(HttpStatus.NOT_FOUND); 
		} else {
			
			LOGGER.info("Uspjesno vracena poruka za odgovarajuci ID"); 

			return new ResponseEntity<MMessageDTO>(new MMessageDTO(message), HttpStatus.OK); 
		}
	}
	
	/**
	 * 
	 * @param id taga
	 * @return vraca tag za koji smo unijeli ID i vraca sve podatke o user-u koji ima account ... 
	 */
	
	@GetMapping(value="/{id}/tags")
	public ResponseEntity<List<TagDTO>> getMessageTags(@PathVariable("id") Integer id) {
		
		MMessage message = messageServiceInt.findOne(id); 
		
		if(message == null) { return new ResponseEntity<List<TagDTO>>(HttpStatus.NOT_FOUND);}
		
		List<Tag> tags = tagService.findByMessage(message);
		List<TagDTO> tagsDto = new ArrayList<TagDTO>(); 
		
		for(Tag tag: tags) {
			tagsDto.add(new TagDTO(tag));
		}
		
		LOGGER.info("Uspjesno vracena lista tagova za poruku"); 

		return new ResponseEntity<List<TagDTO>>(tagsDto, HttpStatus.OK);
	}
	/**
	 * 
	 * @param id proslijedjen id attachmenta 
	 * @return attachment za koji je proslijedjen id 
	 */
	@GetMapping(value="/{id}/attachments")
	public ResponseEntity<List<AttachmentDTO>> getAttachments(@PathVariable("id") Integer id) {
		
		MMessage message = messageServiceInt.findOne(id); 
		
		if(message == null) { return new ResponseEntity<List<AttachmentDTO>>(HttpStatus.NOT_FOUND);}
		
		List<Attachment> attachments = attachmentService.findByMessage(message);
		List<AttachmentDTO> messageAttachmentsDto = new ArrayList<AttachmentDTO>();
		
		for(Attachment attachment: attachments) {
			AttachmentDTO attachmentDTO = new AttachmentDTO(attachment);
			attachmentDTO.setData(null);
			messageAttachmentsDto.add(attachmentDTO);
		}
		
		LOGGER.info("Uspjesno vracena odgovarajuci prilog"); 

		return new ResponseEntity<List<AttachmentDTO>>(messageAttachmentsDto, HttpStatus.OK);
	}
	
	/**
	 * metoda za kreiranje poruke i smjestanje u sent folder 
	 * @param emailDTO bean iz kojeg ce se izvuci svi parametri potrebni za kreiranje mejla 
	 * @param id naloga 
	 * @return novokreirana poruka dodana u Sent folder 
	 */
	@PostMapping(value="/{accountId}", consumes="application/json")
	public ResponseEntity<MMessageDTO> saveMessage(@RequestBody MMessageDTO emailDTO, @PathVariable("accountId") Integer id) {
	
		Account account = accountService.findOne(id); 
		
		if(account == null) {
			return new ResponseEntity<MMessageDTO>(HttpStatus.NOT_FOUND);
		}
		
		//emailDTO.setFrom(account.getUsername());
		
		MMessage message = new MMessage(); 
		//message.setFrom(emailDTO.getMessageDTO().getFrom());
		message.setFrom(account.getUsername());
		message.setTo(emailDTO.getTo());
		message.setCc(emailDTO.getCc());
		message.setBcc(emailDTO.getBcc());
		//message.setDateTime(LocalDateTime.now());
		message.setDateTime(new Timestamp(System.currentTimeMillis()));
		message.setSubject(emailDTO.getSubject());
		message.setContent(emailDTO.getContent());
		message.setUnread(false);
		
		account.addMessage(message);
		addMessageInSentFolder(message,account); 
		
		account = accountService.save(account);
		
		for (AttachmentDTO attachmentDTO : emailDTO.getAttachmentsDTO()) {
			Attachment attachment = new Attachment();
			attachment.setName(attachmentDTO.getName());
			attachment.setMimeType(attachmentDTO.getMimeType());
			attachment.setData("");
			message.addAttachment(attachment);
			
			if (attachmentDTO.getData() != null && !attachmentDTO.getData().isEmpty()) {
				byte[] attachmentData = Base64.decode(attachmentDTO.getData());
				String path = String.format("./data/attachments/%d", new Date().hashCode());
				
				if (FilesUtil.saveBytes(attachmentData, path)) {
					attachment.setData(path);
				}
			}
			
		}
		
		message = messageServiceInt.save(message);
		
		boolean sent = mailApi.sendMessage(message); 
		//System.out.println("Ovo je sadrzaj sent poruke koja odlazi na API" +sent );
		
		if(sent) {
			message = messageServiceInt.save(message);
			//System.out.println("Ovo je poruka koja se vratila kao true ili false sa mail api-ja" + message);
			
			LOGGER.info("Poruka uspjesno poslata!"); 

			return new ResponseEntity<MMessageDTO>(new MMessageDTO(message), HttpStatus.CREATED);
		}
		
		return new ResponseEntity<MMessageDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * metoda koja ce da smjesti poslatu poruku u Sent folder 
	 * @param message poruka koju dodajem u odgovarajuci folder
 	 * @param account nalog za koji dodajem poruku 
	 */
	
	private void addMessageInSentFolder(MMessage message, Account account) {
		for(Folder folder : account.getFolders()) {
			if(folder.getName().equalsIgnoreCase("Outbox")) {
				folder.addMessage(message);
				break; 
			}
		}
	}
	
	/**
	 * metoda pomocu koje neprocitana poruka postaje procitana 
	 * @param id poruke 
	 * @return true, ukoliko se uspjesno odradi setUnread(false) 
	 */
	@PutMapping(value = "/{id}/markAsRead")
	public ResponseEntity<Boolean> markAsRead(@PathVariable("id") Integer id){
		MMessage message = messageServiceInt.findOne(id);
		
		if(message== null) {
			return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
		}
		
		message.setUnread(false);
		message = messageServiceInt.save(message); 
		
		LOGGER.info("Poruka je procitana!"); 

		return new ResponseEntity<Boolean>(true,HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param id poruke 
	 * @param folderId foldera 
	 * @return poruka premjestena u folder sa zadatim id-jem 
	 */
	@PostMapping(value="/{id}/moveTo/{folderId}")
	public ResponseEntity<MMessageDTO> moveMessage(@PathVariable("id")Integer id, @PathVariable("folderId") Integer folderId) {
		MMessage message = messageServiceInt.findOne(id); 
		
		if(message == null) {
			return new ResponseEntity<MMessageDTO>(HttpStatus.NOT_FOUND);
		}
		
		Folder folder = folderService.findOne(folderId);
		if(folder == null) {
			return new ResponseEntity<MMessageDTO>(HttpStatus.NOT_FOUND);
		}
		
		folder.addMessage(message);
		message.setFolder(folder);
		message = messageServiceInt.save(message); 
		
		LOGGER.info("Uspjesno prebacena poruka u drugi folder!"); 
		
		return new ResponseEntity<MMessageDTO>(new MMessageDTO(message), HttpStatus.OK);
	}
	

	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteMessage(@PathVariable("id") Integer id) {
		MMessage message = messageServiceInt.findOne(id); 
		
		if(message != null) {
			messageServiceInt.remove(id);
			
			LOGGER.info("Uspjesno obrisana poruka"); 

			return new ResponseEntity<Void>(HttpStatus.OK); 
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}


	@PostMapping("{accountIndex}/sync")
	public ResponseEntity<?> getMessagesFromServer(@PathVariable("accountIndex") int accountIndex) {
	
		if(accountIndex < 0) {
			return new ResponseEntity<> ("Account nema ID!", HttpStatus.BAD_REQUEST);
		}
		
		Account account = accountService.findAccount(UserController.korisnikID, accountIndex); 		//Account account = accountService.findByAccountId(accountIndex, username);
		
		if(account == null) {
			return new ResponseEntity<>("Nepostojeci nalog!", HttpStatus.NOT_FOUND); 
		}
		
		List<MMessage> messages = mailApi.loadMessages(account);
		System.out.println("Duzina poruka sa mejla " + messages.size());
		
		messages = primjeniPravila(messages,account);
		
		for(MMessage message : messages) {
			messageService.save(message);
		}
		
		account.setLastSyncTime(new Timestamp(System.currentTimeMillis()));
		accountService.save(account);
		
		LOGGER.info("Uspjesno odradjena sinhronizacija poruka sa gmail-a!"); 
		
		return new ResponseEntity<String>("Gotova sinhronizacija!", HttpStatus.OK);
	}
	
	private List<MMessage> primjeniPravila(List<MMessage> messages, Account account) {
		// ----- Obtaining data from database -----
				//List<Folder> folders = folderService.findAll(account);
				List<Folder> folders = folderService.findAllByAccount(account);
				if (folders == null)
					return messages;

				List<Rule> rules = new ArrayList<Rule>();
				for (Folder folder : folders)
					for (Rule rule : folder.getRules())
						rules.add(rule);

				// ----- Applying rules -----
				List<MMessage> updatedMessages = new ArrayList<MMessage>();
				for (MMessage message : messages)
					updatedMessages.add(message);

				for (MMessage message : messages) {
					for (Rule rule : rules) {
						switch (rule.getCondition()) {
						case CC:
							if (message.getCc().contains(rule.getConditionValue())) {
								switch (rule.getOperation()) {
								case COPY:
									MMessage copy = new MMessage(message);

									copy.setFolder(rule.getSourceFolder());

									updatedMessages.add(copy);
									break;
								case MOVE:
									message.setFolder(rule.getSourceFolder());
									if (!updatedMessages.contains(message))
										updatedMessages.add(message);
									break;
								case DELETE:
									if (rule.getSourceFolder().getName().equals("Inbox"))
										updatedMessages.remove(message);
									break;
								}
							}
							break;
						case TO:
							if (message.getTo().contains(rule.getConditionValue())) {
								switch (rule.getOperation()) {
								case COPY:
									MMessage copy = new MMessage(message);

									copy.setFolder(rule.getSourceFolder());

									updatedMessages.add(copy);
									break;
								case MOVE:
									message.setFolder(rule.getSourceFolder());
									if (!updatedMessages.contains(message))
										updatedMessages.add(message);
									break;
								case DELETE:
									if (rule.getSourceFolder().getName().equals("Inbox"))
										updatedMessages.remove(message);
									break;
								}
							}
							break;
						case FROM:
							if (message.getFrom().contains(rule.getConditionValue())) {
								switch (rule.getOperation()) {
								case COPY:
									MMessage copy = new MMessage(message);

									copy.setFolder(rule.getSourceFolder());

									updatedMessages.add(copy);
									break;
								case MOVE:
									message.setFolder(rule.getSourceFolder());
									if (!updatedMessages.contains(message))
										updatedMessages.add(message);
									break;
								case DELETE:
									if (rule.getSourceFolder().getName().equals("Inbox"))
										updatedMessages.remove(message);
									break;
								}
							}
							break;
						case SUBJECT:
							if (message.getSubject().contains(rule.getConditionValue())) {
								switch (rule.getOperation()) {
								case COPY:
									MMessage copy = new MMessage(message);

									copy.setFolder(rule.getSourceFolder());

									updatedMessages.add(copy);
									break;
								case MOVE:
									message.setFolder(rule.getSourceFolder());
									if (!updatedMessages.contains(message))
										updatedMessages.add(message);
									break;
								case DELETE:
									if (rule.getSourceFolder().getName().equals("Inbox"))
										updatedMessages.remove(message);
									break;
								}
							}
							break;
						}
					}
				}

				return updatedMessages;
	}
	
	/**
	 * 
	 * @param accountIndex
	 * @param filterDTO
	 * @return
	 */
	
	@PostMapping(value="/filter/{accountIndex}")
	public ResponseEntity<List<MMessageDTO>> filterMessages(@PathVariable("accountIndex") int accountIndex, @RequestBody FilterDTO filterDTO) {
		
		Account account = accountService.findOne(accountIndex);
		
		if(account == null) {
			return new ResponseEntity<List<MMessageDTO>>(HttpStatus.NOT_FOUND); 
		}
		
		List<MMessage> messages = messageService.findAllByAccount(account); 
		
		if(messages == null) {
			return new ResponseEntity<List<MMessageDTO>>(HttpStatus.NOT_FOUND); 
		}
		
		Set<MMessage> filteredSet = new HashSet<MMessage>(); 
		
		if(filterDTO.getTags().size() > 0) {
			for(MMessage message : messages) {
				loop : for (Tag tag : message.getTags()) {
					for(TagDTO tagDTO : filterDTO.getTags()) {
						if(tag.getId() == tagDTO.getId()) {
							filteredSet.add(message);
							break loop;
						}
					}
				}
			}
		}
		
		if(!filterDTO.getSearch().isEmpty()) {
			for(MMessage message : messages) {
				
				if(message.getSubject().toLowerCase().contains(filterDTO.getSearch().toLowerCase())) {
					filteredSet.add(message); 
					continue; 
				}
				
				if(message.getFrom().toLowerCase().contains(filterDTO.getSearch().toLowerCase())) {
					filteredSet.add(message);
					continue; 
				}
				
				if(message.getTo().toLowerCase().contains(filterDTO.getSearch().toLowerCase())) {
					filteredSet.add(message); 
					continue; 
				}
				
				if(message.getCc().toLowerCase().contains(filterDTO.getSearch().toLowerCase())) {
					filteredSet.add(message); 
					continue; 
				}
				
				if(message.getBcc().toLowerCase().contains(filterDTO.getSearch().toLowerCase())) {
					filteredSet.add(message); 
					continue; 
				}
				
				if(message.getContent().toLowerCase().contains(filterDTO.getSearch().toLowerCase())) {
					filteredSet.add(message); 
					continue;
				}
			}
		}
		
		List<MMessage> filtered = new ArrayList<MMessage>(); 
		filtered.addAll(filteredSet);
		
		List<MMessageDTO> messagesForAccount = new ArrayList<MMessageDTO>(); 
		
		for(int i = 0; i < 50; i++ ) {
			if(i == filtered.size()) 
				break;
			messagesForAccount.add(new MMessageDTO(filtered.get(i)));
		}
		return new ResponseEntity<List<MMessageDTO>>(messagesForAccount, HttpStatus.OK);
	}
	
	
	/**
	 * 	
	 * @param accountIndex
	 * @param principal 
	 * @param sortBy
	 * @param asc
	 * @return
	 */
	@GetMapping("{accountIndex}/sort")
	public ResponseEntity<?> sortMessages(@PathVariable("accountIndex") int accountIndex , @RequestParam String sortBy, @RequestParam String asc) {
		System.out.println("pogodio");
		if(accountIndex < 0) {
			return new ResponseEntity<>("Nalog nema ID!", HttpStatus.BAD_REQUEST);
		}
		
		Account account = accountService.findAccount(UserController.korisnikID, accountIndex); 
		
		if(account == null) {
			return new ResponseEntity<>("Nalog ne postoji!", HttpStatus.NOT_FOUND);
		}
		
		List<MMessageDTO> messagesDTO = new ArrayList<MMessageDTO>(); 
		Comparator<MMessage> comparator; 
		
		if(sortBy.equals("subject") && asc.equals("asc")) {
			comparator = new Comparator<MMessage>() {

				@Override
				public int compare(MMessage o1, MMessage o2) {
					return o1.getSubject().compareTo(o2.getSubject());
				}
			};
		} else if (sortBy.equals("subject") && asc.equals("desc")) {
			comparator = new Comparator<MMessage>() {

				@Override
				public int compare(MMessage o1, MMessage o2) {
					return o1.getSubject().compareTo(o2.getSubject()) * -1; 
				}
			};
		}
		
		else if (sortBy.equals("from") && asc.equals("asc")) {
			comparator = new Comparator<MMessage>() {

				@Override
				public int compare(MMessage o1, MMessage o2) {
					return o1.getFrom().compareTo(o2.getFrom());
				}
			};
		} else if (sortBy.equals("from") && asc.equals("desc")) {
			comparator = new Comparator<MMessage>() {

				@Override
				public int compare(MMessage o1, MMessage o2) {
					return o1.getFrom().compareTo(o2.getFrom()) * -1; 
				}
			};
		}
		
		else if(sortBy.equals("date") && asc.equals("asc")) {
			comparator = new Comparator<MMessage>() {

				@Override
				public int compare(MMessage o1, MMessage o2) {
					return o1.getDateTime().compareTo(o2.getDateTime());
				}
			};
		}
		
		else if(sortBy.equals("date") && asc.equals("desc")) {
			comparator = new Comparator<MMessage>() {

				@Override
				public int compare(MMessage o1, MMessage o2) {
					return o1.getDateTime().compareTo(o2.getDateTime()) * -1; 
				}
			};
		} else {
			comparator = new Comparator<MMessage>() {

				@Override
				public int compare(MMessage o1, MMessage o2) {
					return o1.getId().compareTo(o2.getId());
				}
			};
		}
		
		List<MMessage> messages = messageService.findAllByAccount(account); 
		
		Collections.sort(messages, comparator);
		
		for(MMessage message : messages) {
			messagesDTO.add(new MMessageDTO(message));
		}
		
		LOGGER.info("Uspjesno odradjeno sortiranje poruka"); 

		return new ResponseEntity<List<MMessageDTO>>(messagesDTO, HttpStatus.OK);
	}
	
	
	@GetMapping("{accountIndex}/search")
	public ResponseEntity<?> getSearchedMessages(@PathVariable("accountIndex") int accountIndex,
			@RequestParam Optional<String> userEmail) throws IOException {

		if (accountIndex < 0) {
			return new ResponseEntity<>("You must provide an ID for an account!", HttpStatus.BAD_REQUEST);
		}

		Account account = accountService.findAccount(UserController.korisnikID, accountIndex); 
		if (account == null) {
			return new ResponseEntity<>("Unexisting account.", HttpStatus.NOT_FOUND);
		}

		List<MMessageDTO> messagesDTO = new ArrayList<MMessageDTO>();

		if (userEmail != null) {
			List<MMessage> mess = messageService.findByFrom(userEmail.orElse("_"));
			for (MMessage message : mess) {

				messagesDTO.add(new MMessageDTO(message));
				return new ResponseEntity<List<MMessageDTO>>(messagesDTO, HttpStatus.OK);
			}
		}
		
		LOGGER.info("Uspjesno odradjena pretraga po mejlu! "); 

		return new ResponseEntity<List<MMessageDTO>>(messagesDTO, HttpStatus.OK);

	}

}