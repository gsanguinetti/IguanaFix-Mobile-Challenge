package com.gastonsanguinetti.contacts.model;

import java.io.Serializable;

public class Phone implements Serializable {
	
	private static final long serialVersionUID = 1950731420952052376L;
	
	private String type;
	
	private String number;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
}