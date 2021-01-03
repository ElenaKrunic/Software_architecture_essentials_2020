package com.projekat.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	
	@Autowired
	private MMessageRepository messageRepository;
	
	@Autowired
	private FolderService folderService;
	/*
	@Override
	public List<MMessage> getMessages(Account account) {
		Folder folder = folderService.findInbox(account);
		return messageRepository.findAllByAccountAndFolder(account, folder);
	}*/

	@Override
	public Date findLastDate(Account account) {
		return messageRepository.findLastDate(account.getId());
	}

	@Override
	public MMessage save(MMessage message) {
		return messageRepository.save(message);
	}

	@Override
	public void remove(Integer id) {
		messageRepository.deleteById(id);
	}

	@Override
	public List<MMessage> findByFrom(String userEmail) {
		return messageRepository.findByFromContaining(userEmail);
	}
	
	@Override
	public List<MMessage> findAllUnread(Account account){
		return messageRepository.findAllByUnreadIsTrue();
	}

	@Override
	public List<MMessage> findAll() {
		return messageRepository.findAll();
	}

	@Override
	public List<MMessage> findAllByAccount(Account account) {
		return messageRepository.findAllByAccount(account);
	}

	@Override
	public MMessage findOne(Integer id) {
		return messageRepository.getOne(id);
	}

	public List<MMessage> findByFolder(Folder folder) {
		// TODO Auto-generated method stub
		return messageRepository.findByFolder(folder);
	}
	
	public List<MMessage> findByAccountAndTag(Account account, Tag tag) {
		return messageRepository.findByAccountAndTags(account,tag);
	}

	
	@Override
	public MMessage addTagToMessage(MMessage message, Integer tagId) {
	
		return null;
	//throw new NotImplementedException();	
	}
	
}
