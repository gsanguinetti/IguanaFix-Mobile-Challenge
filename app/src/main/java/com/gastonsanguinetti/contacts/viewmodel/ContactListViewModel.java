package com.gastonsanguinetti.contacts.viewmodel;

import com.gastonsanguinetti.contacts.model.Contact;
import com.gastonsanguinetti.contacts.service.IguanaFixService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactListViewModel extends IguanaFixUseCaseViewModel<List<Contact>>{
	
	@Override
	protected void doLoadData() {
		getRetrofit().create(IguanaFixService.class).getContacts().enqueue(new Callback<List<Contact>>() {
			@Override
			public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
				if (response.isSuccessful()) {
					onSuccess(response.body());
				} else {
					onError();
				}
			}
			
			@Override
			public void onFailure(Call<List<Contact>> call, Throwable t) {
				onError();
			}
		});
	}
}
