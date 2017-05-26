package com.gastonsanguinetti.contacts.viewmodel;

import com.gastonsanguinetti.contacts.model.Contact;
import com.gastonsanguinetti.contacts.service.IguanaFixService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactDetailsViewModel extends IguanaFixUseCaseViewModel<Contact>{
	
	private Contact requestedContact;
	
	public void initialize(Contact requestedContact) {
		this.requestedContact = requestedContact;
	}
	
	@Override
	protected void doLoadData() {
		if(requestedContact != null) {
			getRetrofit()
					.create(IguanaFixService.class)
					.getContactDetails(requestedContact.getUserId())
					.enqueue(new Callback<Contact>() {
						@Override
						public void onResponse(Call<Contact> call, Response<Contact> response) {
							if(response.isSuccessful()) {
								onSuccess(response.body());
							} else {
								onError();
							}
						}
						
						@Override
						public void onFailure(Call<Contact> call, Throwable t) {
							onError();
						}
					});
		} else {
			throw new IllegalStateException("Must initialize the ContactDetailsViewModel before call doLoadData");
		}
	}
}
