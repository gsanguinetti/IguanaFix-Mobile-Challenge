package com.gastonsanguinetti.contacts.model;

import java.io.Serializable;

public class Phone implements Serializable {
	
	private static final long serialVersionUID = 1950731420952052376L;
	
	private String type;
	
	private Object number;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Object getNumber() {
		return number;
	}
	
	public void setNumber(Object number) {
		this.number = number;
	}
	
}