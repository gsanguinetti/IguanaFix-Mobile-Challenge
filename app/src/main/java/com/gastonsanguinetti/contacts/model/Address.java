package com.gastonsanguinetti.contacts.model;

import java.io.Serializable;

public class Address implements Serializable {
	
	private final static long serialVersionUID = -7835109553719868639L;
	
	private String work;
	
	private String home;
	
	public String getWork() {
		return work;
	}
	
	public void setWork(String work) {
		this.work = work;
	}
	
	public String getHome() {
		return home;
	}
	
	public void setHome(String home) {
		this.home = home;
	}
}
