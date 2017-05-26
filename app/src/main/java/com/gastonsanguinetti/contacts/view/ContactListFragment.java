package com.gastonsanguinetti.contacts.view;

import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
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

import java.util.List;

public class ContactListFragment extends UseCaseFragment<List<Contact>> {
	
	View loadingLayout;
	View errorLayout;
	RecyclerView contactListRecyclerView;
	ContactListAdapter contactListAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_contact_list, container, false);
	}
	
	@NonNull
	@Override
	protected Class getViewModelClass() {
		return ContactListViewModel.class;
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
		
		// Start observing events
		getViewModel().getData().observe(this,  new Observer<List<Contact>>() {
			@Override
			public void onChanged(@Nullable List<Contact> contacts) {
				contactListAdapter.setContacts(contacts);
			}
		});
		
	}
	
	@Override
	protected void onUseCaseLoading() {
		loadingLayout.setVisibility(View.VISIBLE);
		contactListRecyclerView.setVisibility(View.GONE);
		errorLayout.setVisibility(View.GONE);
	}
	
	@Override
	protected void onUseCaseError() {
		loadingLayout.setVisibility(View.GONE);
		contactListRecyclerView.setVisibility(View.GONE);
		errorLayout.setVisibility(View.VISIBLE);
		
		final Snackbar errorSnackbar = Snackbar.make(getView(),
				R.string.error_msg,
				Snackbar.LENGTH_INDEFINITE);
		errorSnackbar.setAction(R.string.retry_button, new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getViewModel().getData();
			}
		});
		errorSnackbar.show();
	}
	
	@Override
	protected void onUseCaseFinished() {
		loadingLayout.setVisibility(View.GONE);
		contactListRecyclerView.setVisibility(View.VISIBLE);
		errorLayout.setVisibility(View.GONE);
	}
	
}
