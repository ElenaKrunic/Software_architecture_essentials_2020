package com.projekat.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.demo.dto.TagDTO;
import com.projekat.demo.entity.Account;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Tag;
import com.projekat.demo.repository.MMessageRepository;


@Service
public class MessageService implements MessageServiceInterface {
	
    private static final Logger LOGGER = LogManager.getLogger(MessageService.class);

	@Autowired
	private MMessageRepository messageRepository;
	
	@Autowired
	private FolderService folderService;
	

	@Override
	public Date findLastDate(Account account) {
		LOGGER.info("Metoda iz servisa za pronalazenje posljednjeg datuma slanja ");
		return messageRepository.findLastDate(account.getId());
	}

	@Override
	public MMessage save(MMessage message) {
		LOGGER.info("Metoda iz servisa za cuvanje poruke ");
		return messageRepository.save(message);
	}

	@Override
	public void remove(Integer id) {
		LOGGER.info("Metoda iz servisa za brisanje poruke ");
		messageRepository.deleteById(id);
	}

	@Override
	public List<MMessage> findByFrom(String userEmail) {
		LOGGER.info("Metoda iz servisa za pronalazenje posiljaoca mejla");
		return messageRepository.findByFromContaining(userEmail);
	}
	
	@Override
	public List<MMessage> findAllUnread(Account account){
		LOGGER.info("Metoda iz servisa za pronalazenje neprocitanih poruka ");
		return messageRepository.findAllByUnreadIsTrue();
	}

	@Override
	public List<MMessage> findAll() {
		LOGGER.info("Metoda iz servisa za pronalazenje svih poruka");
		return messageRepository.findAll();
	}

	@Override
	public List<MMessage> findAllByAccount(Account account) {
		LOGGER.info("Metoda iz servisa za pronalazenje svih poruka po nalogu ");
		return messageRepository.findAllByAccount(account);
	}

	@Override
	public MMessage findOne(Integer id) {
		LOGGER.info("Metoda iz servisa za pronalazenje jedne poruke ");
		return messageRepository.getOne(id);
	}

	public List<MMessage> findByFolder(Folder folder) {
		LOGGER.info("Metoda iz servisa za pronalazenje poruke po folderu ");
		return messageRepository.findByFolder(folder);
	}
	
	public List<MMessage> findByAccountAndTag(Account account, Tag tag) {
		LOGGER.info("Metoda iz servisa za pronalazenje po nalogu i tagu");
		return messageRepository.findByAccountAndTags(account,tag);
	}
}
