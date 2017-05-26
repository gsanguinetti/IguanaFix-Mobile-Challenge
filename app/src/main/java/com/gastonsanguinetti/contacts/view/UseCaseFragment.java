package com.gastonsanguinetti.contacts.view;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.gastonsanguinetti.contacts.viewmodel.UseCaseViewModel;

public abstract class UseCaseFragment<T> extends LifecycleFragment {
	
	private UseCaseViewModel<T> viewModel;
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		ViewModel fragmentViewModel = ViewModelProviders.of(this).get(getViewModelClass());
		if (fragmentViewModel instanceof  UseCaseViewModel) {
			this.viewModel = (UseCaseViewModel<T>) fragmentViewModel;
		} else {
			throw new RuntimeException("An UseCaseViewModel needs to be set in UseCaseFragment");
		}
		
		viewModel.getUseCaseStatus().observe(this, new Observer<UseCaseViewModel.Status>() {
			@Override
			public void onChanged(UseCaseViewModel.Status status) {
				switch (status) {
					case IDLE:
						onUseCaseIdle();
						break;
					case LOADING:
						onUseCaseLoading();
						break;
					case ERROR:
						onUseCaseError();
						break;
					case SUCCESS:
						onUseCaseFinished();
						break;
				}
			}
		});
	}
	
	@NonNull
	protected abstract Class getViewModelClass();

	public UseCaseViewModel getViewModel() {
		return viewModel;
	}
	
	protected void onUseCaseIdle() {
		//
	}
	
	protected void onUseCaseLoading() {
		//
	}
	
	protected void onUseCaseError() {
		//
	}
	
	protected void onUseCaseFinished() {
		//
	}
}
