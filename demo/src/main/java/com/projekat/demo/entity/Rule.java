package com.projekat.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="rules")
public class Rule implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 531596767014579078L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "BIGINT", unique = true, nullable = false)
	private Integer id;
	
	@Enumerated
	@Column(columnDefinition = "BIGINT", name = "rule_condition", nullable = false)
	private RuleCondition condition;
	
	@Column(name = "value", nullable = false)
	private String value;
	
	@Enumerated
	@Column(columnDefinition = "BIGINT", name = "rule_operation", nullable = false)
	private RuleOperation operation; 
	
	//@ManyToOne
	//@JoinColumn(name = "folder", referencedColumnName = "id", nullable = true)
	//private Folder folder;
	
	@ManyToOne
	@JoinColumn(name="source_folder_id", referencedColumnName="id", nullable=true)
	private Folder sourceFolder; 
	
	@ManyToOne
	@JoinColumn(name="destination_folder_id", referencedColumnName="id", nullable=true)
	private Folder destinationFolder; 
	
	public enum RuleCondition {
		TO,
		FROM,
		CC,
		SUBJECT
	}
	
	public enum RuleOperation {
		MOVE,
		COPY,
		DELETE
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RuleCondition getCondition() {
		return condition;
	}

	public void setCondition(RuleCondition condition) {
		this.condition = condition;
	}

	public String getConditionValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public RuleOperation getOperation() {
		return operation;
	}

	public void setOperation(RuleOperation operation) {
		this.operation = operation;
	}

	public String getValue() {
		return value;
	}

	public Folder getSourceFolder() {
		return sourceFolder;
	}

	public void setSourceFolder(Folder sourceFolder) {
		this.sourceFolder = sourceFolder;
	}

	public Folder getDestinationFolder() {
		return destinationFolder;
	}

	public void setDestinationFolder(Folder destinationFolder) {
		this.destinationFolder = destinationFolder;
	}
	
	/*
	public MMessage doRule(MMessage message) {
		switch(this.condition) {
			case TO: {
				if (message.getTo().contains(this.value)) {
					return this.doOperation(message);
				}
				break;
			}
			case FROM: {
				if (message.getFrom().contains(this.value)) {
					return this.doOperation(message);	
				}
				break;
			}
			case CC: {
				if (message.getCc().contains(this.value)) {
					return this.doOperation(message);
				}
				break;
			}
			case SUBJECT: {
				if (message.getSubject().contains(this.value)) {
					return this.doOperation(message);
				}
				break;
			}
		}
		return null;
	}
	
	private MMessage doOperation(MMessage message) {
		switch(this.operation) {
			case MOVE: {
				this.folder.addMessage(message);
				return message;
			}
			case COPY: {
				MMessage copy = MMessage.clone(message);
				this.folder.addMessage(copy);
				return copy;
			}
			case DELETE: {
				message.getAccount().removeMessage(message);
				if (message.getFolder() != null)
					message.getFolder().removeMessage(message);
				return message;
			}
			default: {
				return null;
			}
		}
	}
	*/
}