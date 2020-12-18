package com.projekat.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.demo.dto.FolderDTO;
import com.projekat.demo.entity.Folder;
import com.projekat.demo.service.FolderServiceInterface;

@RestController
@RequestMapping(value="api/folders")
public class FolderController {

	@Autowired
	private FolderServiceInterface folderService; 
	
	@GetMapping
	public ResponseEntity<List<FolderDTO>> getFolders() {
		List<Folder> folders = folderService.findAll(); 
		
		List<FolderDTO> dtoFolders = new ArrayList<FolderDTO>();
		
		for(Folder folder: folders) {
			dtoFolders.add(new FolderDTO(folder));
		}
		
		return new ResponseEntity<List<FolderDTO>>(dtoFolders, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<FolderDTO> getFolder(@PathVariable("id") Integer id) {
		Folder folder = folderService.findOne(id);
		
		if(folder == null) {
			return new ResponseEntity<FolderDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<FolderDTO>(new FolderDTO(folder), HttpStatus.OK);
	}
	
	
}
