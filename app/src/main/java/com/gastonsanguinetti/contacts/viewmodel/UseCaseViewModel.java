package com.gastonsanguinetti.contacts.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public abstract class UseCaseViewModel<DATA> extends ViewModel {
	
	public enum Status {
		IDLE,
		LOADING,
		SUCCESS,
		ERROR
	}
	
	private MutableLiveData<DATA> data;
	private MutableLiveData<Status> useCaseStatus;
	
	public UseCaseViewModel() {
		this.useCaseStatus = new MutableLiveData<>();
		setUseCaseStatus(Status.IDLE);
	}
	
	public MutableLiveData<DATA> getData() {
		if(data == null) {
			useCaseStatus.setValue(Status.LOADING);
			this.data = new MutableLiveData<>();
			data.setValue(loadData());
		}
		return data;
	}
	
	public void setData(DATA data) {
		this.data.setValue(data);
	}
	
	public Status getUseCaseStatus() {
		return useCaseStatus.getValue();
	}
	
	private void setUseCaseStatus(Status useCaseStatus) {
		this.useCaseStatus.setValue(useCaseStatus);
	}
	
	protected abstract DATA loadData();
	
	protected void onSuccess(DATA data) {
		setData(data);
		setUseCaseStatus(Status.SUCCESS);
	}
	
	protected void onError() {
		setUseCaseStatus(Status.ERROR);
	}
}