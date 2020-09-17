package com.projekat.demo.dto;

import com.projekat.demo.entity.Rule;

public class RuleDTO {
	
	private Integer id; 
	private Rule.RuleCondition condition; 
	private String value; 
	private Rule.RuleOperation operation;
	
	public RuleDTO(Rule rule) {
		this.id = rule.getId(); 
		this.condition = rule.getCondition(); 
		this.value = rule.getValue(); 
		this.operation = rule.getOperation(); 
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Rule.RuleCondition getCondition() {
		return condition;
	}
	public void setCondition(Rule.RuleCondition condition) {
		this.condition = condition;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Rule.RuleOperation getOperation() {
		return operation;
	}
	public void setOperation(Rule.RuleOperation operation) {
		this.operation = operation;
	} 
	
	
	
}
