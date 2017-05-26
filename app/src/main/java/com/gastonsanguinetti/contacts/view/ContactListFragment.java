package com.gastonsanguinetti.contacts.view;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gastonsanguinetti.contacts.R;
import com.gastonsanguinetti.contacts.model.Contact;
import com.gastonsanguinetti.contacts.viewmodel.ContactListViewModel;
import com.gastonsanguinetti.contacts.viewmodel.UseCaseViewModel;

import java.util.List;

public class ContactListFragment extends LifecycleFragment {
	
	View loadingLayout;
	View errorLayout;
	RecyclerView contactListRecyclerView;
	ContactListAdapter contactListAdapter;
	
	ContactListViewModel contactListViewModel;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_contact_list, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		//Initialize toolbar
		if(getActivity() instanceof AppCompatActivity) {
			Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
			((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
		}
		
		//Get views
		contactListRecyclerView = (RecyclerView) view.findViewById(R.id.list);
		loadingLayout = view.findViewById(R.id.loading);
		errorLayout = view.findViewById(R.id.errorImage);
		
		//Initialize contact list
		contactListRecyclerView.setHasFixedSize(true);
		contactListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		
		contactListAdapter = new ContactListAdapter();
		contactListRecyclerView.setAdapter(contactListAdapter);
		
		//Create the viewmodel
		contactListViewModel = ViewModelProviders.of(this).get(ContactListViewModel.class);
		
		// Start observing events
		contactListViewModel.getData().observe(this,  new Observer<List<Contact>>() {
			@Override
			public void onChanged(@Nullable List<Contact> contacts) {
				contactListAdapter.setContacts(contacts);
			}
		});
		
		contactListViewModel.getUseCaseStatus().observe(this, new Observer<UseCaseViewModel.Status>() {
			@Override
			public void onChanged(@Nullable UseCaseViewModel.Status status) {
				onUseCaseStatusChanged(status);
			}
		});
		
		
	}
	
	private void onUseCaseStatusChanged(UseCaseViewModel.Status status) {
		
		switch (status) {
			
			case LOADING:
				loadingLayout.setVisibility(View.VISIBLE);
				contactListRecyclerView.setVisibility(View.GONE);
				errorLayout.setVisibility(View.GONE);
				break;
			
			case ERROR:
				loadingLayout.setVisibility(View.GONE);
				contactListRecyclerView.setVisibility(View.GONE);
				errorLayout.setVisibility(View.VISIBLE);
				
				final Snackbar errorSnackbar = Snackbar.make(getView(),
						R.string.error_msg,
						Snackbar.LENGTH_INDEFINITE);
				errorSnackbar.setAction(R.string.retry_button, new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						contactListViewModel.getData();
					}
				});
				errorSnackbar.show();
				
				break;
			
			case SUCCESS:
				loadingLayout.setVisibility(View.GONE);
				contactListRecyclerView.setVisibility(View.VISIBLE);
				errorLayout.setVisibility(View.GONE);
				break;
			
			default:
				break;
		}
	}
	
}
