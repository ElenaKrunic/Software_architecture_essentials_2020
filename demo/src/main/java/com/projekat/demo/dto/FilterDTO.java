package com.projekat.demo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FilterDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String search; 
	
	private List<TagDTO> tags = new ArrayList<TagDTO>();

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public List<TagDTO> getTags() {
		return tags;
	}

	public void setTags(List<TagDTO> tags) {
		this.tags = tags;
	}

	public FilterDTO(String search, List<TagDTO> tags) {
		super();
		this.search = search;
		this.tags = tags;
	}

	public FilterDTO() {
		super();
	} 
	
	
}
