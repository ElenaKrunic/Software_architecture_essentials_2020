package com.projekat.demo.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.demo.dto.AttachmentDTO;
import com.projekat.demo.dto.EmailDTO;
import com.projekat.demo.dto.MMessageDTO;
import com.projekat.demo.dto.TagDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Attachment;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Tag;
import com.projekat.demo.entity.User;
import com.projekat.demo.mail.MailAPI;
import com.projekat.demo.repository.AttachmentRepository;
import com.projekat.demo.repository.FolderRepository;
import com.projekat.demo.service.AccountService;
import com.projekat.demo.service.AttachmentService;
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
	
	@Autowired
	private MessageServiceInterface messageService; 
	
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
	
	
	/**
	 * 
	 * @return vraca sve poruke iz baze 
	 */
	@GetMapping

	public ResponseEntity<List<MMessageDTO>> getMessages() {
		
		List<MMessage> messages = messageService.findAll(); 
		
		List<MMessageDTO> dtoMessages = new ArrayList<MMessageDTO>();
		
		for(MMessage message : messages) {
			dtoMessages.add(new MMessageDTO(message));
		}
		
		return new ResponseEntity<List<MMessageDTO>>(dtoMessages, HttpStatus.OK);
	}
	/**
	 * 
	 * @param id proslijedjen id poruke 
	 * @return vraca poruku koja ima odredjeni ID 
	 */
	
	@GetMapping(value="/{id}")
	public ResponseEntity<MMessageDTO> getMessage(@PathVariable("id") Integer id) {
		MMessage message = messageService.findOne(id);
		
		if(message == null) {
			return new ResponseEntity<MMessageDTO>(HttpStatus.NOT_FOUND); 
		} else {
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
		
		MMessage message = messageService.findOne(id); 
		
		if(message == null) { return new ResponseEntity<List<TagDTO>>(HttpStatus.NOT_FOUND);}
		
		List<Tag> tags = tagService.findByMessage(message);
		List<TagDTO> tagsDto = new ArrayList<TagDTO>(); 
		
		for(Tag tag: tags) {
			tagsDto.add(new TagDTO(tag));
		}
		
		return new ResponseEntity<List<TagDTO>>(tagsDto, HttpStatus.OK);
	}
	/**
	 * 
	 * @param id proslijedjen id attachmenta 
	 * @return attachment za koji je proslijedjen id 
	 */
	@GetMapping(value="/{id}/attachments")
	public ResponseEntity<List<AttachmentDTO>> getAttachments(@PathVariable("id") Integer id) {
		
		MMessage message = messageService.findOne(id); 
		
		if(message == null) { return new ResponseEntity<List<AttachmentDTO>>(HttpStatus.NOT_FOUND);}
		
		List<Attachment> attachments = attachmentService.findByMessage(message);
		List<AttachmentDTO> messageAttachmentsDto = new ArrayList<AttachmentDTO>();
		
		for(Attachment attachment: attachments) {
			AttachmentDTO attachmentDTO = new AttachmentDTO(attachment);
			attachmentDTO.setData(null);
			messageAttachmentsDto.add(attachmentDTO);
		}
		
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
		
		MMessage message = new MMessage(); 
		//message.setFrom(emailDTO.getMessageDTO().getFrom());
		message.setFrom(emailDTO.getFrom());
		message.setTo(emailDTO.getTo());
		message.setCc(emailDTO.getCc());
		message.setBcc(emailDTO.getBcc());
		//message.setDateTime(LocalDateTime.now());
		message.setDateTime(LocalDateTime.now());
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
		
		message = messageService.save(message);
		
		boolean sent = mailApi.sendMessage(message); 
		//System.out.println("Ovo je sadrzaj sent poruke koja odlazi na API" +sent );
		
		if(sent) {
			message = messageService.save(message);
			System.out.println("Ovo je poruka koja se vratila kao true ili false sa mail api-ja" + message);
			
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
			if(folder.getName().equalsIgnoreCase("Sent")) {
				folder.addMessage(message);
				break; 
			}
		}
	}
	
	//NEURADJENO: 
	// 1.metoda za filtriranje poruka naloga -> PostMapping
	// 2.metoda za update messageTag-ova -> PutMapping 
	
	// 2.metoda za oznacavanje procitanjeporuke -> PutMapping
	/**
	 * metoda pomocu koje neprocitana poruka postaje procitana 
	 * @param id poruke 
	 * @return true, ukoliko se uspjesno odradi setUnread(false) 
	 */
	@PutMapping(value = "/{id}/markAsRead")
	public ResponseEntity<Boolean> markAsRead(@PathVariable("id") Integer id){
		MMessage message = messageService.findOne(id);
		
		if(message== null) {
			return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
		}
		
		message.setUnread(false);
		message = messageService.save(message); 
		return new ResponseEntity<Boolean>(true,HttpStatus.OK);
	}
	
	//4.metoda za pomjeranje poruke -> PutMapping  
	/**
	 * 
	 * @param id poruke 
	 * @param folderId foldera 
	 * @return poruka premjestena u folder sa zadatim id-jem 
	 */
	@PutMapping(value="/{id}/moveTo/{folderId}")
	public ResponseEntity<MMessageDTO> moveMessage(@PathVariable("id")Integer id, @PathVariable("folderId") Integer folderId) {
		MMessage message = messageService.findOne(id); 
		
		if(message == null) {
			return new ResponseEntity<MMessageDTO>(HttpStatus.NOT_FOUND);
		}
		
		Folder folder = folderService.findOne(folderId);
		if(folder == null) {
			return new ResponseEntity<MMessageDTO>(HttpStatus.NOT_FOUND);
		}
		
		folder.addMessage(message);
		message = messageService.save(message); 
		
		return new ResponseEntity<MMessageDTO>(new MMessageDTO(message), HttpStatus.OK);
	}
	
	//5.metoda za brisanje poruke -> DeleteMapping 

	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteMessage(@PathVariable("id") Integer id) {
		MMessage message = messageService.findOne(id); 
		
		if(message != null) {
			//message.getAccount().removeMessage(message);
			//message.getFolder().removeMessage(message);
			
			//for(Tag tag : message.getTags()) {
				//message.removeTag(tag);
			//}
			messageService.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK); 
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

	//=================================  metoda za filtriranje poruka naloga ==========================================================
	
	/*
	
	@PutMapping(value="/{id}", consumes = "application/json")
	public ResponseEntity<MMessageDTO> updateMessage(@RequestBody MMessageDTO messageDTO, @PathVariable("id") Integer id) {
		MMessage message = messageService.findOne(id); 
		
		if(message == null) {
			return new ResponseEntity<MMessageDTO>(HttpStatus.BAD_REQUEST); 
		}
		
		message.setContent(messageDTO.getContent());
		message.setSubject(messageDTO.getSubject());
		message.setDateTime(messageDTO.getDateTime());
		
		messageService.save(message); 
		
		return new ResponseEntity<MMessageDTO>(new MMessageDTO(message), HttpStatus.OK); 
	}
	
	@PostMapping(value="/filterMessages/{accountId}")
	public ResponseEntity<List<MMessageDTO>> filterMessagesForAccount(@RequestBody FilterDTO filter, @PathVariable("accountId") Integer id){
		Account account = accountService.findOne(id); 
		
		if(account == null) {
			return new ResponseEntity<List<MMessageDTO>>(HttpStatus.NOT_FOUND); 
		}
		
		List<MMessage> messages = messageService.findAllByAccount(account);
		Set<MMessage> filtering = new HashSet<MMessage>();
		
		if(filter.getTags().size() > 0) {
			for(MMessage message : messages) {
				tagLoop : for(Tag tag : message.getTags()) {
					for(TagDTO tagDTO : filter.getTags()) {
						if(tag.getId() == tagDTO.getId()) {
							filtering.add(message);
							break tagLoop;
						}
					}
				}
			}
		}
		
		if(filter.getSearchText().isEmpty()) {
			for(MMessage message : messages) {
				if(message.getFrom().toLowerCase().contains(filter.getSearchText().toLowerCase())) {
					filtering.add(message);
					continue;
				}
				if(message.getCc().toLowerCase().contains(filter.getSearchText().toLowerCase())) {
					filtering.add(message);
					continue;
				}
				if(message.getBcc().toLowerCase().contains(filter.getSearchText().toLowerCase())) {
					filtering.add(message);
					continue;
				}
				if(message.getSubject().toLowerCase().contains(filter.getSearchText().toLowerCase())) {
					filtering.add(message);
					continue;
				}
				if(message.getContent().toLowerCase().contains(filter.getSearchText().toLowerCase())) {
					filtering.add(message);
					continue;
				}
			}
		
		}
		
		List<MMessage> filteredMessages = new ArrayList<MMessage>(); 
		filteredMessages.addAll(filtering);
		
		List<MMessageDTO> accountMessages = new ArrayList<MMessageDTO>();
		for (int i = 0; i < 50; i ++) {
			if ( i == filteredMessages.size())
				break;
			accountMessages.add(new MMessageDTO(filteredMessages.get(i)));
		}
		
		return new ResponseEntity<List<MMessageDTO>>(accountMessages, HttpStatus.OK);
	}
		
	}
	*/
}

