package com.gastonsanguinetti.contacts.service;

import com.gastonsanguinetti.contacts.model.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IguanaFixService {
	
	@GET("contacts")
	Call<List<Contact>> getContacts();
	
	@GET("contacts/{id}")
	Call<Contact> getContactDetails(@Path("id") String id);
	
}
