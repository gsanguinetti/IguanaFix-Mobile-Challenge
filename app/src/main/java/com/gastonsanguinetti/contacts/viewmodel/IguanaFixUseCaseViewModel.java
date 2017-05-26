package com.gastonsanguinetti.contacts.viewmodel;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class IguanaFixUseCaseViewModel<DATA> extends UseCaseViewModel<DATA> {
	
	private static final String ENDPOINT_URL = "https://private-d0cc1-iguanafixtest.apiary-mock.com";
	
	protected Retrofit getRetrofit() {
		return new Retrofit.Builder()
				.baseUrl(ENDPOINT_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
	}
}
