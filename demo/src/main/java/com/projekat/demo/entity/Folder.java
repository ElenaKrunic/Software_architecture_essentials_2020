package com.projekat.demo.entity;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="folders")
public class Folder {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id", unique = true, nullable = false)
		private Integer id;
		
		@Column(name = "name", nullable = false)
		private String name;
		
		@ManyToOne
		@JsonIgnore
		@JoinColumn(name = "account", referencedColumnName = "id", nullable = false)
		private Account account;
		
		@ManyToOne
		@JsonIgnore
		@JoinColumn(name = "parent_folder", referencedColumnName = "id", nullable = true)
		private Folder parentFolder;
		
		@OneToMany(cascade = { REMOVE, PERSIST }, fetch = LAZY, mappedBy = "parentFolder")
		@JsonIgnore
		private List<Folder> subFolders;
		
		@OneToMany(cascade = { REMOVE, PERSIST }, fetch = LAZY, mappedBy = "folder", orphanRemoval = true)
		@JsonIgnore
	    private List<Rule> rules;

	    @OneToMany(cascade = { REMOVE }, fetch = FetchType.EAGER, mappedBy = "folder")
		private List<MMessage> messages;
		
		public Folder() {
			
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<Rule> getRules() {
			return rules;
		}

		public void setRules(List<Rule> rules) {
			this.rules = rules;
		}
		
		public Account getAccount() {
			return account;
		}

		public void setAccount(Account account) {
			this.account = account;
		}

		public Folder getParentFolder() {
			return parentFolder;
		}

		public void setParentFolder(Folder parentFolder) {
			this.parentFolder = parentFolder;
		}
		
		public List<MMessage> getMessages() {
			return messages;
		}

		public void setMessages(List<MMessage> messages) {
			this.messages = messages;
		}

		public void fixRules() {
			for (Rule rule : rules) {
				rule.setFolder(this);
			}
		}
		
		public List<Folder> getSubFolders() {
			return subFolders;
		}

		public void setSubFolders(List<Folder> subFolders) {
			this.subFolders = subFolders;
		}

		@Override
		public String toString() {
			return "Folder [id=" + id + ", name=" + name + ", account=" + account + ", subFolders=" + subFolders
					 + ", rules=" + rules + "]";
		}
		
}
