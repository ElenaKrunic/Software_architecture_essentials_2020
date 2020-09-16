package com.projekat.demo.entity;

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
public class Rule {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer id; 
	
	@Enumerated
	@Column(name="condition", nullable=false)
	private RuleCondition condition;
	
	@Column(name="value", nullable=false)
	private String value; 
	
	@Enumerated
	@Column(name="operation", nullable=false)
	private RuleOperation operation;
	
	//veze many to one sa folder 
	@ManyToOne
	@JoinColumn(name="folder", referencedColumnName = "id", nullable=true)
	private Folder folder;

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

	public String getValue() {
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

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public Rule(Integer id, RuleCondition condition, String value, RuleOperation operation, Folder folder) {
		super();
		this.id = id;
		this.condition = condition;
		this.value = value;
		this.operation = operation;
		this.folder = folder;
	}

	public Rule() {
		super();
	}

	
}
